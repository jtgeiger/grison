package com.sibilantsolutions.grison.driver.foscam.domain;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.sibilantsolutions.grison.util.Convert;

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
    private short cameraPort;           //INT16_R (2 bytes; big endian)

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

    public short getCameraPort()
    {
        return cameraPort;
    }

    public void setCameraPort( short cameraPort )
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

    static public InitReqText parse( String data )
    {
        InitReqText text = new InitReqText();

        int i = 0;

        i += 4;

        String cameraId = data.substring( i, i += 13 );
        cameraId = cameraId.trim();
        text.cameraId = cameraId;

        String username = data.substring( i, i += 13 );
        username = username.trim();
        text.username = username;

        String password = data.substring( i, i += 13 );
        password = password.trim();
        text.password = password;

        text.cameraIP = SearchRespText.getByAddress( data.substring( i, i += 4 ) );
        text.netmask = SearchRespText.getByAddress( data.substring( i, i += 4 ) );
        text.gatewayIP = SearchRespText.getByAddress( data.substring( i, i += 4 ) );
        text.dnsIP = SearchRespText.getByAddress( data.substring( i, i += 4 ) );
        text.cameraPort = (short)Convert.toNum( data.substring( i, i += 2 ) );

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
        bb.putShort( cameraPort );

        return bb.array();
    }

}
