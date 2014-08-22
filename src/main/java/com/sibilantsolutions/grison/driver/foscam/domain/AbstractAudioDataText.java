package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract public class AbstractAudioDataText implements DatastreamI
{

    private long uptimeMs;              //INT32 (4 bytes; little endian); 10ms for Audio Data (we scale to 1ms), 1ms for Talk Data (Foscam docs call this field=timestamp)
    private long serialNumber;          //INT32 (4 bytes; little endian); increase from zero
    private long timestampMs;           //INT32 (4 bytes; little endian); 1s (we scale to ms) From 1970.1.1 to current time (Foscam docs call this field=gatherTime)
    private AudioFormatE audioFormat;   //INT8; =0: adpcm
    //private long dataLength;          //INT32 (4 bytes; little endian); =160
    private byte[] dataContent;         //BINARY_STREAM[n]

    private int uptimeScale;

    protected AbstractAudioDataText( int uptimeScale )
    {
        this.uptimeScale = uptimeScale;
    }

    public AudioFormatE getAudioFormat()
    {
        return audioFormat;
    }

    public void setAudioFormat( AudioFormatE audioFormat )
    {
        this.audioFormat = audioFormat;
    }

    public byte[] getDataContent()
    {
        return dataContent;
    }

    public void setDataContent( byte[] dataContent )
    {
        this.dataContent = dataContent;
    }

    public long getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber( long serialNumber )
    {
        this.serialNumber = serialNumber;
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

    protected void parseImpl( byte[] data, int offset, int length )
    {
        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        this.uptimeMs = ( (long)bb.getInt() ) * uptimeScale;
        this.serialNumber = bb.getInt();
        this.timestampMs = ( (long)bb.getInt() ) * 1000;

        this.audioFormat = AudioFormatE.fromValue( (char)bb.get() );

        int dataLen = bb.getInt();

        byte[] dc = new byte[dataLen];
        bb.get( dc );
        this.dataContent = dc;
    }

    @Override
    public byte[] toDatastream()
    {
        ByteBuffer bb = ByteBuffer.allocate( 4 + 4 + 4 + 1 + 4 + dataContent.length );  //177
        bb.order( ByteOrder.LITTLE_ENDIAN );

        bb.putInt( (int)( uptimeMs / uptimeScale ) );
        bb.putInt( (int)serialNumber );
        bb.putInt( (int)( timestampMs / 1000 ) );
        bb.put( (byte)audioFormat.getValue() );
        bb.putInt( dataContent.length );
        bb.put( dataContent );

        return bb.array();
    }

}
