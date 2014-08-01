package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.sibilantsolutions.grison.util.Convert;

abstract public class AbstractStartRespText implements DatastreamI
{

    private ResultCodeE resultCode;     //INT16 (2 bytes; little endian)
    private String dataConnectionId;    //INT32 (4 bytes; little endian)

    public ResultCodeE getResultCode()
    {
        return resultCode;
    }

    public void setResultCode( ResultCodeE resultCode )
    {
        this.resultCode = resultCode;
    }

    public String getDataConnectionId()
    {
        return dataConnectionId;
    }

    public void setDataConnectionId( String dataConnectionId )
    {
        this.dataConnectionId = dataConnectionId;
    }

    protected void parseImpl( byte[] data, int offset, int length )
    {
        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        this.resultCode = ResultCodeE.fromValue( bb.getShort() );

            //If an audio/video connection is already open and another start request is sent,
            //the response will have resultCode==0 and no data connection id.
        if ( bb.hasRemaining() )
        {
            this.dataConnectionId = Convert.get( 4, bb );
        }
    }

    @Override
    public byte[] toDatastream()
    {
        ByteBuffer bb = ByteBuffer.allocate( 2 + 4 );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        bb.putShort( resultCode.getValue() );
        Convert.put( dataConnectionId, bb );

        return bb.array();
    }

}
