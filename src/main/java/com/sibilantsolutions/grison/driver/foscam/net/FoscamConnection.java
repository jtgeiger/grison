package com.sibilantsolutions.grison.driver.foscam.net;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.AlarmNotifyText;
import com.sibilantsolutions.grison.driver.foscam.domain.AudioDataText;
import com.sibilantsolutions.grison.driver.foscam.domain.AudioVideoProtocolOpCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.driver.foscam.domain.KeepAliveText;
import com.sibilantsolutions.grison.driver.foscam.domain.OpCodeI;
import com.sibilantsolutions.grison.driver.foscam.domain.OperationProtocolOpCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.domain.SearchProtocolOpCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.Unk02Text;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;
import com.sibilantsolutions.iptools.event.LostConnectionEvt;
import com.sibilantsolutions.iptools.event.ReceiveEvt;
import com.sibilantsolutions.iptools.event.SocketListenerI;
import com.sibilantsolutions.iptools.net.LengthByteBuffer;
import com.sibilantsolutions.iptools.net.LengthByteBuffer.LengthByteType;
import com.sibilantsolutions.iptools.net.ReceiveQueue;
import com.sibilantsolutions.iptools.net.SocketUtils;
import com.sibilantsolutions.utils.util.DurationLoggingCallable;
import com.sibilantsolutions.utils.util.DurationLoggingRunnable;
import com.sibilantsolutions.utils.util.HexUtils;

public class FoscamConnection
{

    final static private Logger log = LoggerFactory.getLogger( FoscamConnection.class );
    final static private Logger keepAliveLog = LoggerFactory.getLogger( log.getName() + ".keepalive" );

        //These will only come in response to a corresponding request.
        //All other opcodes are unsolicited.
    final static private List<? extends OpCodeI> synchronousResponseOpcodes = Arrays.asList(
        OperationProtocolOpCodeE.Audio_Start_Resp, OperationProtocolOpCodeE.Login_Resp,
        OperationProtocolOpCodeE.Talk_Start_Resp, OperationProtocolOpCodeE.Verify_Resp,
        OperationProtocolOpCodeE.Video_Start_Resp,
        SearchProtocolOpCodeE.Init_Resp, SearchProtocolOpCodeE.Search_Resp );

    final private Socket socket;
    final private ExecutorService executorService;
    final private ScheduledExecutorService keepAliveService;

    final private BlockingQueue<Command> q = new LinkedBlockingQueue<Command>();

    final private FoscamSessionI owner;

    private FoscamConnection( Socket socket, ProtocolE protocol, FoscamSessionI owner )
    {
        this.socket = socket;
        this.owner = owner;

        this.executorService = createExecutorService( "executor " + this.socket );

        this.keepAliveService = createScheduledExecutorService( "keepAlive " + this.socket );
        scheduleKeepAlive( protocol, this.keepAliveService );

        SocketListenerI dest = new ReceiverProducer( q );

        dest = new LengthByteBuffer( 0x0F, 4, LengthByteType.LENGTH_OF_PAYLOAD,
                ByteOrder.LITTLE_ENDIAN, 4, 0xFFFF, dest );

        dest = new ReceiveQueue( dest );

        SocketUtils.readLoopThread( 0xFFFF, this.socket, dest );
    }

    static public FoscamConnection connect( InetSocketAddress address, ProtocolE protocol, FoscamSessionI owner )
    {
        Socket socket = SocketUtils.connect( address );

        FoscamConnection connection = new FoscamConnection( socket, protocol, owner );

        return connection;
    }

    static private ExecutorService createExecutorService( final String threadName )
    {
        ExecutorService es = Executors.newSingleThreadExecutor( new ThreadFactory() {

            @Override
            public Thread newThread( Runnable r )
            {
                Thread thread = new Thread( r, threadName );
                thread.setDaemon( true );
                return thread;
            }
        } );

        return es;
    }

    static private ScheduledExecutorService createScheduledExecutorService( final String threadName )
    {
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor( new ThreadFactory() {

            @Override
            public Thread newThread( Runnable r )
            {
                Thread thread = new Thread( r, threadName );
                thread.setDaemon( true );
                return thread;
            }
        } );

        return ses;
    }

    private <T> T execute( Callable<T> task )
    {
        Future<T> future = executorService.submit( task );

        T result;

        try
        {
            result = future.get( 15, TimeUnit.SECONDS );
        }
        catch ( InterruptedException | ExecutionException | TimeoutException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        return result;
    }

    protected void sendAsync( final Command request )
    {
        Runnable task = new Runnable() {

            @Override
            public void run()
            {
                log.info( "Send async: p={}, o={}.", request.getProtocol(), request.getOpCode() );

                SocketUtils.send( request.toDatastream(), socket );
            }
        };

        task = new DurationLoggingRunnable( task,
                "sendAsync p=" + request.getProtocol() + ", o=" + request.getOpCode() );

        execute( Executors.callable( task ) );
    }

    protected Command sendReceive( final Command request )
    {
        Callable<Command> task = new Callable<Command>() {

            @Override
            public Command call() throws Exception
            {
                log.info( "Send request: p={}, o={}.", request.getProtocol(), request.getOpCode() );

                SocketUtils.send( request.toDatastream(), socket );

                Command response = q.take();

                log.info( "Got response: p={}, o={}.", response.getProtocol(), response.getOpCode() );

                return response;
            }
        };

        task = new DurationLoggingCallable<Command>( task,
                "sendReceive p=" + request.getProtocol() + ", o=" + request.getOpCode() );

        return execute( task );
    }

    private void scheduleKeepAlive( final ProtocolE keepAliveProtocol, ScheduledExecutorService service )
    {
        final OpCodeI keepAliveOpCode;
        switch ( keepAliveProtocol )
        {
            case OPERATION_PROTOCOL:
                keepAliveOpCode = OperationProtocolOpCodeE.Keep_Alive;
                break;

            case AUDIO_VIDEO_PROTOCOL:
                keepAliveOpCode = AudioVideoProtocolOpCodeE.Keep_Alive;
                break;

            default:
                throw new IllegalArgumentException( "Unexpected protocol=" + keepAliveProtocol );
        }

        Runnable r = new Runnable() {

            @Override
            public void run()
            {
                keepAliveLog.info( "Sending scheduled keep alive for socket={}.", socket );

                Command c = new Command();
                c.setProtocol( keepAliveProtocol );
                c.setOpCode( keepAliveOpCode );
                KeepAliveText text = new KeepAliveText();
                c.setCommandText( text );

                sendAsync( c );
            }
        };

        service.scheduleAtFixedRate( r, 60, 60, TimeUnit.SECONDS );
    }

    private class ReceiverProducer implements SocketListenerI
    {
        private final BlockingQueue<Command> queue;

        public ReceiverProducer( BlockingQueue<Command> queue )
        {
            this.queue = queue;
        }

        @Override
        public void onLostConnection( LostConnectionEvt evt )
        {
            log.info( "Lost connection on socket={}.", socket );

            keepAliveService.shutdownNow();
            executorService.shutdownNow();

            owner.onLostConnection( FoscamConnection.this );
        }

        @Override
        public void onReceive( ReceiveEvt evt )
        {
            byte[] bytes = evt.getData();
            int offset = evt.getOffset();
            int nbytes = evt.getLength();

            //log.info( "Receive packet: \n{}", HexDumpDeferred.prettyDump( bytes, offset, nbytes ) );

            Command command = Command.parse( bytes, offset, nbytes );

            ProtocolE protocol = command.getProtocol();
            OpCodeI opCode = command.getOpCode();

            log.info( "Receive command (len=0x{}/{}): p={}, o={}.",
                    HexUtils.numToHex( nbytes ), nbytes, protocol, opCode );

            if ( ( protocol == ProtocolE.OPERATION_PROTOCOL &&
                    opCode == OperationProtocolOpCodeE.Keep_Alive ) ||
                 ( protocol == ProtocolE.AUDIO_VIDEO_PROTOCOL &&
                    opCode == AudioVideoProtocolOpCodeE.Keep_Alive ) )
            {
                keepAliveLog.info( "Incoming keep alive p={}, o={}.", protocol, opCode );
                return;
            }

            if ( synchronousResponseOpcodes.contains( opCode ) )
            {
                queue.add( command );
            }
            else
            {
//                log.info( "UNSOLICITED!" );
                //TODO fire to an UnsolicitedHandlerI.

                if ( protocol == ProtocolE.AUDIO_VIDEO_PROTOCOL )
                {
                    AudioVideoProtocolOpCodeE avOpCode = (AudioVideoProtocolOpCodeE)opCode;

                    switch ( avOpCode )
                    {
                        case Audio_Data:
                            owner.onReceiveAudio( (AudioDataText)command.getCommandText() );
                            break;

                        case Video_Data:
                            owner.onReceiveVideo( (VideoDataText)command.getCommandText() );
                            break;

                        default:
                            throw new RuntimeException( "Unexpected opcode=" + avOpCode );
                    }
                }
                else if ( protocol == ProtocolE.OPERATION_PROTOCOL )
                {
                    OperationProtocolOpCodeE opOpCode = (OperationProtocolOpCodeE)opCode;

                    switch ( opOpCode )
                    {
                        case Alarm_Notify:
                            AlarmNotifyText ant = (AlarmNotifyText)command.getCommandText();
                            owner.onAlarm( ant );
                            break;

                        case UNK02:
                            Unk02Text unk02 = (Unk02Text)command.getCommandText();
                            byte[] unk02data = unk02.getData();
                            boolean isOnlyNull = true;
                            for ( int i = 0; i < unk02data.length && isOnlyNull; i++ )
                            {
                                isOnlyNull = ( unk02data[i] == 0x00 );
                            }
                            log.info( "Received {}, data length={}, is only nulls={}.",
                                    opOpCode, unk02data.length, isOnlyNull );
                            break;

                        default:
                            throw new RuntimeException( "Unexpected opcode=" + opOpCode );
                    }
                }
            }
        }

    }

}
