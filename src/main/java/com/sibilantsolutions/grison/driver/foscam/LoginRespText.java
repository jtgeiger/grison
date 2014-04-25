package com.sibilantsolutions.grison.driver.foscam;

import com.sibilantsolutions.grison.util.Convert;

public class LoginRespText implements DatastreamI
{

    private int result;                 //INT16 (2 bytes; little endian)
    private String cameraId;            //BINARY_STREAM[13]
    //private String RESERVE            //BINARY_STREAM[4]
    //private String RESERVE            //BINARY_STREAM[4]
    private String firmwareVersion;     //BINARY_STREAM[4]

    public int getResult()
    {
        return result;
    }

    public void setResult( int result )
    {
        this.result = result;
    }

    public String getCameraId()
    {
        return cameraId;
    }

    public void setCameraId( String cameraId )
    {
        this.cameraId = cameraId;
    }

    public String getFirmwareVersion()
    {
        return firmwareVersion;
    }

    public void setFirmwareVersion( String firmwareVersion )
    {
        this.firmwareVersion = firmwareVersion;
    }

    @Override
    public String toDatastream()
    {
        StringBuilder buf = new StringBuilder( 2 + 13 + 4 + 4 + 4 );

        buf.append( Convert.toLittleEndian( result, 2 ) );

        buf.append( cameraId ); //12 bytes
        buf.append( (char)0x00 );   //Null terminator.

            //RESERVED
        //for ( int i = 0; i < 4; i++ )
        //    buf.append( (char)0x00 );
        buf.append( "" + (char)0x00 + (char)0x00 + (char)0x00 + (char)0x01 );

            //RESERVED
        for ( int i = 0; i < 4; i++ )
            buf.append( (char)0x00 );

        buf.append( firmwareVersion );

        return buf.toString();
    }

}
