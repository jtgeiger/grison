package com.sibilantsolutions.grison.driver.foscam;

import com.sibilantsolutions.grison.util.Convert;

public class SearchRespText implements DatastreamI
{

    private String cameraId;            //BINARY_STREAM[13]
    private String cameraName;          //BINARY_STREAM[21]
    private long cameraIP;              //INT32_R (4 bytes; big endian)
    private long netmask;               //INT32_R (4 bytes; big endian)
    private long gatewayIP;             //INT32_R (4 bytes; big endian)
    private long dnsIP;                 //INT32_R (4 bytes; big endian)
    //private String RESERVE            //BINARY_STREAM[4]
    private String sysSoftwareVersion;  //BINARY_STREAM[4]
    private String appSoftwareVersion;  //BINARY_STREAM[4]
    private long cameraPort;            //INT16_R (2 bytes; big endian)
    private boolean dhcpEnabled;        //INT8

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

        text.cameraIP = Convert.toNum( data.substring( i, i += 4 ) );
        text.netmask = Convert.toNum( data.substring( i, i += 4 ) );
        text.gatewayIP = Convert.toNum( data.substring( i, i += 4 ) );
        text.dnsIP = Convert.toNum( data.substring( i, i += 4 ) );

        i += 4;

        text.sysSoftwareVersion = data.substring( i, i += 4 );
        text.appSoftwareVersion = data.substring( i, i += 4 );

        text.cameraPort = Convert.toNum( data.substring( i, i += 2 ) );

        text.dhcpEnabled = ( data.charAt( i ) == 1 );

        return text;
    }

    @Override
    public String toDatastream()
    {
        StringBuilder buf = new StringBuilder( 13 + 21 + 4 + 4 + 4 + 4 + 4 + 4 + 4 + 2 + 1 );

        //TODO: Truncate strings if necessary; pad if necessary.

        //return buf.toString();
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

}
