package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VideoDataText implements DatastreamI
{

    private long uptimeMs;      //INT32 (4 bytes; little endian); 10ms (we scale to 1ms) (Foscam docs call this field=timestamp)
    private long timestampMs;   //INT32 (4 bytes; little endian); 1s (we scale to ms) From 1970.1.1 to current time (Foscam docs call this field=framesPerSec)
    //private RESERVE           //INT8
    //private long dataLength;  //INT32 (4 bytes; little endian);
    private byte[] dataContent; //BINARY_STREAM[n]

    public byte[] getDataContent()
    {
        return dataContent;
    }

    public void setDataContent( byte[] dataContent )
    {
        this.dataContent = dataContent;
    }

    public long getTimestampMs()
    {
        return timestampMs;
    }

    public void setTimestampMs( long timestampMs )
    {
        this.timestampMs = timestampMs;
    }

    public long getUptimeMs()
    {
        return uptimeMs;
    }

    public void setUptimeMs( long uptimeMs )
    {
        this.uptimeMs = uptimeMs;
    }

    public static VideoDataText parse( byte[] data, int offset, int length )
    {
        VideoDataText text = new VideoDataText();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        text.uptimeMs = ( (long)bb.getInt() ) * 10;
        text.timestampMs = ( (long)bb.getInt() ) * 1000;

        bb.position( bb.position() + 1 );   //RESERVE

        int dataLen = bb.getInt();

        byte[] dc = new byte[dataLen];
        bb.get( dc );
        text.dataContent = dc;

        return text;
    }

    @Override
    public byte[] toDatastream()
    {
        ByteBuffer bb = ByteBuffer.allocate( 4 + 4 + 1 + 4 + dataContent.length );  //177
        bb.order( ByteOrder.LITTLE_ENDIAN );

        bb.putInt( (int)( uptimeMs / 10 ) );
        bb.putInt( (int)( timestampMs / 1000 ) );
        bb.put( (byte)0 );  //RESERVE
        bb.putInt( dataContent.length );
        bb.put( dataContent );

        return bb.array();
    }

}
