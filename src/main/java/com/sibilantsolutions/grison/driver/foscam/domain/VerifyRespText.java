package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VerifyRespText implements DatastreamI
{

    private ResultCodeE resultCode;     //INT16 (2 bytes; little endian)
    //private int RESERVE               //INT8

    public ResultCodeE getResultCode()
    {
        return resultCode;
    }

    public void setResultCode( ResultCodeE resultCode )
    {
        this.resultCode = resultCode;
    }

    public static VerifyRespText parse( byte[] data, int offset, int length )
    {
        VerifyRespText text = new VerifyRespText();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        short resultCodeNum = bb.getShort();

        text.resultCode = ResultCodeE.fromValue( resultCodeNum );

            //RESERVED
        bb.position( bb.position() + 1 );

        return text;
    }

    @Override
    public byte[] toDatastream()
    {
        ByteBuffer bb = ByteBuffer.allocate( 2 + 1 );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        bb.putShort( resultCode.getValue() );

            //RESERVED
        bb.put( (byte)0x00 );

        return bb.array();
    }

}
