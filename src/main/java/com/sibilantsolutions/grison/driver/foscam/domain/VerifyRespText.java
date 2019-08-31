package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;

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

        text.resultCode = ResultCodeE.fromValue(FosInt16.create(resultCodeNum));

        /*
         * When result code is CORRECT, then there is an extra byte.  But when result code is USER_WRONG, there is no
         * extra byte.
         */
        if (bb.position() < length) {

            //RESERVED
            bb.position(bb.position() + 1);
        }

        return text;
    }

    @Override
    public byte[] toDatastream()
    {
        int capacity = 2;
        if (resultCode == ResultCodeE.CORRECT) {
            capacity++;
        }

        ByteBuffer bb = ByteBuffer.allocate(capacity);
        bb.order( ByteOrder.LITTLE_ENDIAN );

        bb.putShort(resultCode.value.toShort());

        /*
         * When result code is CORRECT, then there is an extra byte.  But when result code is USER_WRONG, there is no
         * extra byte.
         */
        if (resultCode == ResultCodeE.CORRECT) {
            //RESERVED
            bb.put((byte) 0x00);
        }

        return bb.array();
    }

}
