package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;

public class LoginRespText implements DatastreamI
{

    private ResultCodeE resultCode;     //INT16 (2 bytes; little endian)
    private String cameraId;            //BINARY_STREAM[13]
    //private String RESERVE            //BINARY_STREAM[4]
    //private String RESERVE            //BINARY_STREAM[4]
    private Version firmwareVersion;    //BINARY_STREAM[4]

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

    public Version getFirmwareVersion()
    {
        return firmwareVersion;
    }

    public void setFirmwareVersion( Version firmwareVersion )
    {
        this.firmwareVersion = firmwareVersion;
    }

    static public LoginRespText parse( byte[] data, int offset, int length )
    {
        LoginRespText text = new LoginRespText();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        short resultCodeNum = bb.getShort();

        text.resultCode = ResultCodeE.fromValue(FosInt16.create(resultCodeNum));

        String cameraId = Convert.get( 13, bb );
        cameraId = cameraId.trim();
        text.cameraId = cameraId;

        bb.position( bb.position() + 4 );
        bb.position( bb.position() + 4 );

        byte major = bb.get();
        byte minor = bb.get();
        byte patch = bb.get();
        byte build = bb.get();

        text.firmwareVersion = new Version( major, minor, patch, build );

        return text;
    }

    @Override
    public byte[] toDatastream()
    {
        ByteBuffer bb = ByteBuffer.allocate( 2 + 13 + 4 + 4 + 4 );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        bb.putShort(resultCode.value.value());

        Convert.put( Convert.padRearOrTruncate( cameraId, 12, (char)0 ), bb ); //12 bytes
        bb.put( (byte)0x00 );   //Null terminator.

            //RESERVED
        bb.put( new byte[] { 0, 0, 0, 1 } );

            //RESERVED
        for ( int i = 0; i < 4; i++ )
            bb.put( (byte)0x00 );

        bb.put( (byte)firmwareVersion.getMajor() );
        bb.put( (byte)firmwareVersion.getMinor() );
        bb.put( (byte)firmwareVersion.getPatch() );
        bb.put( (byte)firmwareVersion.getBuild() );

        return bb.array();
    }

}
