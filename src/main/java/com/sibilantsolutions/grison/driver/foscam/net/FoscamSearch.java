package com.sibilantsolutions.grison.driver.foscam.net;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.domain.SearchProtocolOpCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.SearchReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.SearchRespText;
import com.sibilantsolutions.iptools.event.DatagramReceiveEvt;
import com.sibilantsolutions.iptools.event.DatagramReceiverI;
import com.sibilantsolutions.iptools.net.SocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

abstract public class FoscamSearch
{
    final static private Logger log = LoggerFactory.getLogger( FoscamSearch.class );

    private FoscamSearch() {}   //Prevent instantiation.

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
        } catch (Exception e)   // In JDK <= 1.7, this constructor threw SocketException. In JDK >= 1.8, it doesn't.
        {
            // TODO Auto-generated catch block
            // Do not need explicit catch here in JDK >= 1.8.
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

}
