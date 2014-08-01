package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract public class AbstractAudioDataText implements DatastreamI
{

    private long timestamp;             //INT32 (4 bytes; little endian); 10ms
    private long serialNumber;          //INT32 (4 bytes; little endian); increase from zero
    private long gatherTime;            //INT32 (4 bytes; little endian); From 1970.1.1 to current time
    private AudioFormatE audioFormat;   //INT8; =0: adpcm
    //private long dataLength;          //INT32 (4 bytes; little endian); =160
    private byte[] dataContent;         //BINARY_STREAM[n]

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp( long timestamp )
    {
        this.timestamp = timestamp;
    }

    public long getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber( long serialNumber )
    {
        this.serialNumber = serialNumber;
    }

    public long getGatherTime()
    {
        return gatherTime;
    }

    public void setGatherTime( long gatherTime )
    {
        this.gatherTime = gatherTime;
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

    protected void parseImpl( byte[] data, int offset, int length )
    {
        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        this.timestamp = bb.getInt();
        this.serialNumber = bb.getInt();
        this.gatherTime = bb.getInt();

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

        bb.putInt( (int)timestamp );
        bb.putInt( (int)serialNumber );
        bb.putInt( (int)gatherTime );
        bb.put( (byte)audioFormat.getValue() );
        bb.putInt( dataContent.length );
        bb.put( dataContent );

        return bb.array();
    }

}
