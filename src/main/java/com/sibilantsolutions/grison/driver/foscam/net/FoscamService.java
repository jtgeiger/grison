package com.sibilantsolutions.grison.driver.foscam.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.AudioStartReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.AudioStartRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.driver.foscam.domain.LoginReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.LoginRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.OperationProtocolOpCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.domain.SearchProtocolOpCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.SearchReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.SearchRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.TalkStartReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.TalkStartRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.VerifyReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.VerifyRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoStartReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoStartRespText;
import com.sibilantsolutions.iptools.event.DatagramReceiveEvt;
import com.sibilantsolutions.iptools.event.DatagramReceiverI;
import com.sibilantsolutions.iptools.net.SocketUtils;

public class FoscamService
{
    final static private Logger log = LoggerFactory.getLogger( FoscamService.class );

    private FoscamConnection connection;

    public FoscamService( FoscamConnection connection )
    {
        this.connection = connection;
    }

    public AudioStartRespText audioStart()
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Audio_Start_Req );
        AudioStartReqText audio = new AudioStartReqText();
        c.setCommandText( audio );
        audio.setData( 1 );

        Command response = sendReceive( c );

            //Blind cast; what can we do about it if the wrong thing was received?
        return (AudioStartRespText)response.getCommandText();
    }

    public void audioVideoLogin( String dataConnectionId )
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.AUDIO_VIDEO_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Login_Req );
        LoginReqText login = new LoginReqText();
        c.setCommandText( login );
        login.setDataConnectionId( dataConnectionId );

        connection.sendNoReceive( c );
    }

    static public List<SearchRespText> deviceSearch( int searchDurationMs )
    {
        return deviceSearch( searchDurationMs, 0 );    //Choose a random port.
    }

    static public List<SearchRespText> deviceSearch( int searchDurationMs, int localPort )
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.SEARCH_PROTOCOL );
        c.setOpCode( SearchProtocolOpCodeE.Search_Req );
        SearchReqText text = new SearchReqText();
        c.setCommandText( text );

        byte[] buf = c.toDatastream();

            //This address:port is defined in the Foscam docs.
        InetSocketAddress destinationAddress = new InetSocketAddress( "255.255.255.255", 10000 );

        final DatagramPacket packet;
        try
        {
            packet = new DatagramPacket( buf, buf.length, destinationAddress );
        }
        catch ( SocketException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "MY TODO!", e );
        }

        final DatagramSocket socket;
        try
        {
            //Make sure that your firewall is open to receive UDP packets on this local port.
            socket = new DatagramSocket( localPort );
        }
        catch ( SocketException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "MY TODO!", e );
        }

        SocketUtils.send( packet, socket );

        final List<SearchRespText> searchResponses = new ArrayList<SearchRespText>();

        DatagramReceiverI receiver = new DatagramReceiverI() {

            @Override
            public void onReceive( DatagramReceiveEvt evt )
            {
                DatagramPacket receivePacket = evt.getPacket();

                Command cReceive = Command.parse( receivePacket.getData(),
                        receivePacket.getOffset(), receivePacket.getLength() );

                switch ( cReceive.getProtocol() )
                {

                    case SEARCH_PROTOCOL:
                        SearchProtocolOpCodeE opCode = (SearchProtocolOpCodeE)cReceive.getOpCode();
                        switch ( opCode )
                        {
                            case Search_Resp:
                                SearchRespText srt = (SearchRespText)cReceive.getCommandText();
                                searchResponses.add( srt );
                                break;

                            default:
                                throw new IllegalArgumentException( "Unexpected opcode=" + opCode );

                        }
                        break;

                    default:
                        throw new IllegalArgumentException( "Unexpected protocol=" + cReceive.getProtocol() );
                };
            }
        };

        byte[] receiveBuf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket( receiveBuf, receiveBuf.length );

        log.info( "Starting to listen on socket={} for search responses.  Make sure UDP port={} is allowed through any firewall.",
                socket.getLocalSocketAddress(), socket.getLocalPort() );

        SocketUtils.receiveLoopThread( receivePacket, socket, receiver );

        log.info( "Sleeping {}ms to wait for search responses.", searchDurationMs );

        try
        {
            Thread.sleep( searchDurationMs );
        }
        catch ( InterruptedException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "MY TODO!", e );
        }

        log.info( "Awake after {}ms; done waiting for search responses.", searchDurationMs );

        socket.close();

        return searchResponses;
    }

    public LoginRespText login()
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Login_Req );
        LoginReqText login = new LoginReqText();
        c.setCommandText( login );
        login.setDataConnectionId( "" );

        Command response = sendReceive( c );

            //Blind cast; what can we do about it if the wrong thing was received?
        return (LoginRespText)response.getCommandText();
    }

    private Command sendReceive( Command request )
    {
        return connection.sendReceive( request );
    }

    public TalkStartRespText talkStart()
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Talk_Start_Req );
        TalkStartReqText talk = new TalkStartReqText();
        c.setCommandText( talk );
        talk.setData( 1 );

        Command response = sendReceive( c );

            //Blind cast; what can we do about it if the wrong thing was received?
        return (TalkStartRespText)response.getCommandText();
    }

    public VerifyRespText verify( final String username, final String password )
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Verify_Req );
        VerifyReqText verify = new VerifyReqText();
        c.setCommandText( verify );
        verify.setUsername( username );
        verify.setPassword( password );

        Command response = sendReceive( c );

            //Blind cast; what can we do about it if the wrong thing was received?
        return (VerifyRespText)response.getCommandText();
    }

    public VideoStartRespText videoStart()
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Video_Start_Req );
        VideoStartReqText video = new VideoStartReqText();
        c.setCommandText( video );
        video.setData( 1 );

        Command response = sendReceive( c );

            //Blind cast; what can we do about it if the wrong thing was received?
        return (VideoStartRespText)response.getCommandText();
    }

}
