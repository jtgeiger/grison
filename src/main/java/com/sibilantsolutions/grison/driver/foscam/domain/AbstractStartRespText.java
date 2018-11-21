package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;

abstract public class AbstractStartRespText implements DatastreamI
{

    private ResultCodeE resultCode;     //INT16 (2 bytes; little endian)
    private byte[] dataConnectionId;    //INT32 (4 bytes; little endian)

    public ResultCodeE getResultCode()
    {
        return resultCode;
    }

    public void setResultCode( ResultCodeE resultCode )
    {
        this.resultCode = resultCode;
    }

    public byte[] getDataConnectionId()
    {
        return dataConnectionId;
    }

    public void setDataConnectionId(byte[] dataConnectionId)
    {
        this.dataConnectionId = dataConnectionId;
    }

    protected void parseImpl( byte[] data, int offset, int length )
    {
        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        this.resultCode = ResultCodeE.fromValue(FosInt16.create(bb.getShort()));

            //If an audio/video connection is already open and another start request is sent,
            //the response will have resultCode==0 and no data connection id.
        if ( bb.hasRemaining() )
        {
            this.dataConnectionId = new byte[4];

            bb.get(this.dataConnectionId);
        }
    }

    @Override
    public byte[] toDatastream()
    {
        ByteBuffer bb = ByteBuffer.allocate( 2 + 4 );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        bb.putShort(resultCode.value.value());
        bb.put(dataConnectionId);

        return bb.array();
    }

}
