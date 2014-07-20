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
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.driver.foscam.domain.OpCodeI;
import com.sibilantsolutions.grison.driver.foscam.domain.OperationProtocolOpCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.domain.SearchProtocolOpCodeE;
import com.sibilantsolutions.grison.util.Convert;
import com.sibilantsolutions.iptools.event.ReceiveEvt;
import com.sibilantsolutions.iptools.event.SocketListenerI;
import com.sibilantsolutions.iptools.util.LengthByteBuffer;
import com.sibilantsolutions.iptools.util.LengthByteBuffer.LengthByteType;
import com.sibilantsolutions.iptools.util.Socker;

public class FoscamConnection
{

    final static private Logger log = LoggerFactory.getLogger( FoscamConnection.class );

    private Socket socket;

    private ExecutorService executorService = Executors.newSingleThreadExecutor( new ThreadFactory() {

        @Override
        public Thread newThread( Runnable r )
        {
            Thread thread = new Thread( r, "my executor" );
            thread.setDaemon( true );
            return thread;
        }
    });

    private BlockingQueue<Command> q = new LinkedBlockingQueue<Command>();

        //These will only come in response to a corresponding request.
        //All other opcodes are unsolicited.
    final static private List<? extends OpCodeI> synchronousResponseOpcodes = Arrays.asList(
        OperationProtocolOpCodeE.Audio_Start_Resp, OperationProtocolOpCodeE.Login_Resp,
        OperationProtocolOpCodeE.Talk_Start_Resp, OperationProtocolOpCodeE.Verify_Resp,
        OperationProtocolOpCodeE.Video_Start_Resp,
        SearchProtocolOpCodeE.Init_Resp, SearchProtocolOpCodeE.Search_Resp );

    private FoscamConnection( Socket socket )
    {
        this.socket = socket;

        SocketListenerI dest = new ReceiverProducer( q );

        LengthByteBuffer buffer = new LengthByteBuffer( 0x0F, 4, LengthByteType.LENGTH_OF_PAYLOAD,
                ByteOrder.LITTLE_ENDIAN, 4, 0xFFFF, dest );

        Socker.readLoopThread( socket, buffer );
    }

    static public FoscamConnection connect( InetSocketAddress address )
    {
        Socket socket = Socker.connect( address );

        FoscamConnection connection = new FoscamConnection( socket );

        return connection;
    }

    private <T> T execute( Callable<T> task )
    {
        Future<T> future = executorService.submit( task );

        T text;

        try
        {
            text = future.get( 15, TimeUnit.SECONDS );
        }
        catch ( InterruptedException | ExecutionException | TimeoutException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        return text;
    }

    protected Command sendReceive( final Command request )
    {
        Callable<Command> task = new Callable<Command>() {

            @Override
            public Command call() throws Exception
            {
                log.info( "Send request: p={}, o={}.", request.getProtocol(), request.getOpCode() );

                Socker.send( request.toDatastream(), socket );

                Command response = q.take();

                return response;
            }
        };

        return execute( task );
    }

    static private class ReceiverProducer implements SocketListenerI
    {
        private BlockingQueue<Command> queue;

        public ReceiverProducer( BlockingQueue<Command> queue )
        {
            this.queue = queue;
        }

        @Override
        public void onReceive( ReceiveEvt evt )
        {
            byte[] bytes = evt.getData();
            int offset = evt.getOffset();
            int nbytes = evt.getLength();

            //log.info( "Receive packet: \n{}", HexDumpDeferred.prettyDump( bytes, offset, nbytes ) );

            String data = new String( bytes, offset, nbytes, Convert.cs );

            Command command = Command.parse( data );

            ProtocolE protocol = command.getProtocol();
            OpCodeI opCode = command.getOpCode();

            log.info( "Receive command: p={}, o={}.", protocol, opCode );

            if ( protocol == ProtocolE.OPERATION_PROTOCOL )
            {
                OperationProtocolOpCodeE o = (OperationProtocolOpCodeE)opCode;

                if ( o == OperationProtocolOpCodeE.Keep_Alive )
                {
                    log.info( "Auto-responding to keep alive request." );
                    keepAlive( evt );
                    return;
                }
            }

            if ( synchronousResponseOpcodes.contains( opCode ) )
            {
                queue.add( command );
            }
            else
            {
                log.info( "UNSOLICITED!" );
                //TODO fire to an UnsolicitedHandlerI.
            }
        }

        private void keepAlive( ReceiveEvt evt )
        {
            Command c = new Command();
            c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
            c.setOpCode( OperationProtocolOpCodeE.Keep_Alive );

            String datastream = c.toDatastream();
            Socker.send( datastream, evt.getSource() );
        }

    }

}