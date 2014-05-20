package com.sibilantsolutions.grison.driver.foscam;

import com.sibilantsolutions.grison.util.Convert;

public class LoginRespText implements DatastreamI
{

    private ResultCodeE resultCode;     //INT16 (2 bytes; little endian)
    private String cameraId;            //BINARY_STREAM[13]
    //private String RESERVE            //BINARY_STREAM[4]
    //private String RESERVE            //BINARY_STREAM[4]
    private String firmwareVersion;     //BINARY_STREAM[4]

    public ResultCodeE getResultCode()
    {
        return resultCode;
    }

    public void setResultCode( ResultCodeE resultCode )
    {
        this.resultCode = resultCode;
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

    static public LoginRespText parse( String data )
    {
        LoginRespText text = new LoginRespText();

        int i = 0;

        int resultCodeNum = (int)Convert.toNumLittleEndian( data.substring( i, i += 2 ) );

        text.resultCode = ResultCodeE.fromValue( resultCodeNum );

        String cameraId = data.substring( i, i += 13 );
        cameraId = cameraId.trim();
        text.cameraId = cameraId;

        i += 4;
        i += 4;

        String firmwareVersion = data.substring( i, i += 4 );
        text.firmwareVersion = firmwareVersion;

        return text;
    }

    @Override
    public String toDatastream()
    {
        StringBuilder buf = new StringBuilder( 2 + 13 + 4 + 4 + 4 );

        buf.append( Convert.toLittleEndian( resultCode.getValue(), 2 ) );

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
