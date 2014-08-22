package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VideoDataText implements DatastreamI
{

    private long uptimeHundredths;  //INT32 (4 bytes; little endian); (Foscam docs call this field=timestamp)
    private long timestampSeconds;  //INT32 (4 bytes; little endian); From 1970.1.1 to current time (Foscam docs call this field=framesPerSec)
    //private RESERVE               //INT8
    //private long dataLength;      //INT32 (4 bytes; little endian);
    private byte[] dataContent;     //BINARY_STREAM[n]

    public byte[] getDataContent()
    {
        return dataContent;
    }

    public void setDataContent( byte[] dataContent )
    {
        this.dataContent = dataContent;
    }

    public long getTimestampSeconds()
    {
        return timestampSeconds;
    }

    public void setTimestampSeconds( long timestampSeconds )
    {
        this.timestampSeconds = timestampSeconds;
    }

    public long getUptimeHundredths()
    {
        return uptimeHundredths;
    }

    public void setUptimeHundredths( long uptimeHundredths )
    {
        this.uptimeHundredths = uptimeHundredths;
    }

    public static VideoDataText parse( byte[] data, int offset, int length )
    {
        VideoDataText text = new VideoDataText();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        text.uptimeHundredths = bb.getInt();
        text.timestampSeconds = bb.getInt();

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

        bb.putInt( (int)uptimeHundredths );
        bb.putInt( (int)timestampSeconds );
        bb.put( (byte)0 );  //RESERVE
        bb.putInt( dataContent.length );
        bb.put( dataContent );

        return bb.array();
    }

}
