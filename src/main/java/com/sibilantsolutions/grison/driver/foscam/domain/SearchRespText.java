package com.sibilantsolutions.grison.driver.foscam.domain;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import com.sibilantsolutions.grison.util.Convert;

public class SearchRespText implements DatastreamI
{

    private String cameraId;            //BINARY_STREAM[13]
    private String cameraName;          //BINARY_STREAM[21]
    private InetAddress cameraIP;       //INT32_R (4 bytes; big endian)
    private InetAddress netmask;        //INT32_R (4 bytes; big endian)
    private InetAddress gatewayIP;      //INT32_R (4 bytes; big endian)
    private InetAddress dnsIP;          //INT32_R (4 bytes; big endian)
    //private String RESERVE            //BINARY_STREAM[4]
    private String sysSoftwareVersion;  //BINARY_STREAM[4]
    private String appSoftwareVersion;  //BINARY_STREAM[4]
    private int cameraPort;             //INT16_R (2 bytes; big endian)
    private boolean dhcpEnabled;        //INT8

    public String getCameraId()
    {
        return cameraId;
    }

    public void setCameraId( String cameraId )
    {
        this.cameraId = cameraId;
    }

    public String getCameraName()
    {
        return cameraName;
    }

    public void setCameraName( String cameraName )
    {
        this.cameraName = cameraName;
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

    public String getSysSoftwareVersion()
    {
        return sysSoftwareVersion;
    }

    public void setSysSoftwareVersion( String sysSoftwareVersion )
    {
        this.sysSoftwareVersion = sysSoftwareVersion;
    }

    public String getAppSoftwareVersion()
    {
        return appSoftwareVersion;
    }

    public void setAppSoftwareVersion( String appSoftwareVersion )
    {
        this.appSoftwareVersion = appSoftwareVersion;
    }

    public int getCameraPort()
    {
        return cameraPort;
    }

    public void setCameraPort( int cameraPort )
    {
        this.cameraPort = cameraPort;
    }

    public boolean isDhcpEnabled()
    {
        return dhcpEnabled;
    }

    public void setDhcpEnabled( boolean dhcpEnabled )
    {
        this.dhcpEnabled = dhcpEnabled;
    }

    static public SearchRespText parse( byte[] data, int offset, int length )
    {
        SearchRespText text = new SearchRespText();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );

        String cameraId = Convert.get( 13, bb );
        cameraId = cameraId.trim();
        text.cameraId = cameraId;

        String cameraName = Convert.get( 21, bb );
        cameraName = cameraName.trim();
        text.cameraName = cameraName;

        byte[] addrBuf = new byte[4];
        bb.get( addrBuf );
        text.cameraIP = getByAddress( addrBuf );
        bb.get( addrBuf );
        text.netmask = getByAddress( addrBuf );
        bb.get( addrBuf );
        text.gatewayIP = getByAddress( addrBuf );
        bb.get( addrBuf );
        text.dnsIP = getByAddress( addrBuf );

        bb.position( bb.position() + 4 );

        text.sysSoftwareVersion = Convert.get( 4, bb );
        text.appSoftwareVersion = Convert.get( 4, bb );

        text.cameraPort = bb.getChar();

        text.dhcpEnabled = ( bb.get() == 1 );

        return text;
    }

    @Override
    public byte[] toDatastream()
    {
        final int LEN = 13 + 21 + 4 + 4 + 4 + 4 + 4 + 4 + 4 + 2 + 1;
        ByteBuffer bb = ByteBuffer.allocate( LEN );

        Convert.put( Convert.padRearOrTruncate( cameraId, 12, (char)0 ), bb );
        bb.put( (byte)0 );
        Convert.put( Convert.padRearOrTruncate( cameraName, 20, (char)0 ), bb );
        bb.put( (byte)0 );

        bb.put( cameraIP.getAddress() );
        bb.put( netmask.getAddress() );
        bb.put( gatewayIP.getAddress() );
        bb.put( dnsIP.getAddress() );

        for ( int i = 0; i < 4; i++ )
            bb.put( (byte)0 );

        Convert.put( Convert.padRearOrTruncate( sysSoftwareVersion, 4, (char)0 ), bb );
        Convert.put( Convert.padRearOrTruncate( appSoftwareVersion, 4, (char)0 ), bb );

        bb.putShort( (short)cameraPort );

        bb.put( dhcpEnabled ? (byte)1: (byte)0 );

        return bb.array();
    }

    static public InetAddress getByAddress( byte[] bytes )
    {
        try
        {
            return InetAddress.getByAddress( bytes );
        }
        catch ( UnknownHostException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }
    }

}
