package com.sibilantsolutions.grison.driver.foscam;

import java.net.InetAddress;
import java.net.UnknownHostException;

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

    static public SearchRespText parse( String data )
    {
        SearchRespText text = new SearchRespText();

        int i = 0;

        String cameraId = data.substring( i, i += 13 );
        cameraId = cameraId.trim();
        text.cameraId = cameraId;

        String cameraName = data.substring( i, i += 21 );
        cameraName = cameraName.trim();
        text.cameraName = cameraName;

        text.cameraIP = getByAddress( data.substring( i, i += 4 ) );
        text.netmask = getByAddress( data.substring( i, i += 4 ) );
        text.gatewayIP = getByAddress( data.substring( i, i += 4 ) );
        text.dnsIP = getByAddress( data.substring( i, i += 4 ) );

        i += 4;

        text.sysSoftwareVersion = data.substring( i, i += 4 );
        text.appSoftwareVersion = data.substring( i, i += 4 );

        text.cameraPort = (int)Convert.toNum( data.substring( i, i += 2 ) );

        text.dhcpEnabled = ( data.charAt( i ) == 1 );

        return text;
    }

    @Override
    public String toDatastream()
    {
        final int LEN = 13 + 21 + 4 + 4 + 4 + 4 + 4 + 4 + 4 + 2 + 1;
        StringBuilder buf = new StringBuilder( LEN );

        buf.append( Convert.padRearOrTruncate( cameraId, 12, (char)0 ) );
        buf.append( (char)0 );
        buf.append( Convert.padRearOrTruncate( cameraName, 20, (char)0 ) );
        buf.append( (char)0 );

        buf.append( new String( cameraIP.getAddress(), Convert.cs ) );
        buf.append( new String( netmask.getAddress(), Convert.cs ) );
        buf.append( new String( gatewayIP.getAddress(), Convert.cs ) );
        buf.append( new String( dnsIP.getAddress(), Convert.cs ) );

        for ( int i = 0; i < 4; i++ )
            buf.append( (char)0 );

        buf.append( Convert.padRearOrTruncate( sysSoftwareVersion, 4, (char)0 ) );
        buf.append( Convert.padRearOrTruncate( appSoftwareVersion, 4, (char)0 ) );

        buf.append( Convert.toBigEndian( cameraPort, 2 ) );

        buf.append( dhcpEnabled ? (char)1: (char)0 );

        return buf.toString();
    }

    static public InetAddress getByAddress( String str )
    {
        return getByAddress( str.getBytes( Convert.cs ) );
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
