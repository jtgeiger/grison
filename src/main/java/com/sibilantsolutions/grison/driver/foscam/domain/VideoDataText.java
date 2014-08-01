package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VideoDataText implements DatastreamI
{

    private long timestamp;             //INT32 (4 bytes; little endian);
    private long framesPerSec;          //INT32 (4 bytes; little endian); From 1970.1.1 to current time
    //private RESERVE                   //INT8
    //private long dataLength;          //INT32 (4 bytes; little endian);
    private byte[] dataContent;         //BINARY_STREAM[n]

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp( long timestamp )
    {
        this.timestamp = timestamp;
    }

    public long getFramesPerSec()
    {
        return framesPerSec;
    }

    public void setFramesPerSec( long framesPerSec )
    {
        this.framesPerSec = framesPerSec;
    }

    public byte[] getDataContent()
    {
        return dataContent;
    }

    public void setDataContent( byte[] dataContent )
    {
        this.dataContent = dataContent;
    }

    public static VideoDataText parse( byte[] data, int offset, int length )
    {
        VideoDataText text = new VideoDataText();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        text.timestamp = bb.getInt();
        text.framesPerSec = bb.getInt();

        bb.position( bb.position() + 1 );    //RESERVE

        int dataLen = bb.getInt();

        byte[] dc = new byte[dataLen];
        bb.get( dc );
        text.dataContent = dc;

        return text;
    }

    @Override
    public byte[] toDatastream()
    {
        ByteBuffer bb = ByteBuffer.allocate( 4 + 4 + 1 + 4 + dataContent.length );    //177
        bb.order( ByteOrder.LITTLE_ENDIAN );

        bb.putInt( (int)timestamp );
        bb.putInt( (int)framesPerSec );
        bb.put( (byte)0 );    //RESERVE
        bb.putInt( dataContent.length );
        bb.put( dataContent );

        return bb.array();
    }

}
