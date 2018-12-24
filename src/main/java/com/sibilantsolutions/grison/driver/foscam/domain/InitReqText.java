package com.sibilantsolutions.grison.driver.foscam.domain;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class InitReqText implements DatastreamI
{

    //private RESERVE                   //INT8=0
    //private RESERVE                   //INT8=0
    //private RESERVE                   //INT8=0
    //private RESERVE                   //INT8=1
    private String cameraId;            //BINARY_STREAM[13]
    private String username;            //BINARY_STREAM[13]
    private String password;            //BINARY_STREAM[13]
    private InetAddress cameraIP;       //INT32_R (4 bytes; big endian)
    private InetAddress netmask;        //INT32_R (4 bytes; big endian)
    private InetAddress gatewayIP;      //INT32_R (4 bytes; big endian)
    private InetAddress dnsIP;          //INT32_R (4 bytes; big endian)
    private int cameraPort;             //INT16_R (2 bytes; big endian)

    public String getCameraId()
    {
        return cameraId;
    }

    public void setCameraId( String cameraId )
    {
        this.cameraId = cameraId;
    }

    public InetAddress getCameraIP()
    {
        return cameraIP;
    }

    public void setCameraIP( InetAddress cameraIP )
    {
        this.cameraIP = cameraIP;
    }

    public InetAddress getNetmask()
    {
        return netmask;
    }

    public void setNetmask( InetAddress netmask )
    {
        this.netmask = netmask;
    }

    public InetAddress getGatewayIP()
    {
        return gatewayIP;
    }

    public void setGatewayIP( InetAddress gatewayIP )
    {
        this.gatewayIP = gatewayIP;
    }

    public InetAddress getDnsIP()
    {
        return dnsIP;
    }

    public void setDnsIP( InetAddress dnsIP )
    {
        this.dnsIP = dnsIP;
    }

    public int getCameraPort()
    {
        return cameraPort;
    }

    public void setCameraPort( int cameraPort )
    {
        this.cameraPort = cameraPort;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    static public InitReqText parse( byte[] data, int offset, int length )
    {
        InitReqText text = new InitReqText();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );

        bb.position( bb.position() + 4 );

        String cameraId = Convert.get( 13, bb );
        cameraId = cameraId.trim();
        text.cameraId = cameraId;

        String username = Convert.get( 13, bb );
        username = username.trim();
        text.username = username;

        String password = Convert.get( 13, bb );
        password = password.trim();
        text.password = password;

        byte[] addrBuf = new byte[4];
        bb.get( addrBuf );
        text.cameraIP = SearchRespText.getByAddress( addrBuf );
        bb.get( addrBuf );
        text.netmask = SearchRespText.getByAddress( addrBuf );
        bb.get( addrBuf );
        text.gatewayIP = SearchRespText.getByAddress( addrBuf );
        bb.get( addrBuf );
        text.dnsIP = SearchRespText.getByAddress( addrBuf );

        text.cameraPort = bb.getChar();

        return text;
    }

    @Override
    public byte[] toDatastream()
    {
        final int LEN = 1 + 1 + 1 + 1 + 13 + 13 + 13 + 4 + 4 + 4 + 4 + 2;
        ByteBuffer bb = ByteBuffer.allocate( LEN );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        bb.put( new byte[]{ 0, 0, 0, 1 } );

        Convert.put( Convert.padRearOrTruncate( cameraId, 12, (char)0 ), bb );
        bb.put( (byte)0 );
        Convert.put( Convert.padRearOrTruncate( username, 12, (char)0 ), bb );
        bb.put( (byte)0 );
        Convert.put( Convert.padRearOrTruncate( password, 12, (char)0 ), bb );
        bb.put( (byte)0 );

        bb.order( ByteOrder.BIG_ENDIAN );
        bb.put( cameraIP.getAddress() );
        bb.put( netmask.getAddress() );
        bb.put( gatewayIP.getAddress() );
        bb.put( dnsIP.getAddress() );
        bb.putShort( (short)cameraPort );

        return bb.array();
    }

}
