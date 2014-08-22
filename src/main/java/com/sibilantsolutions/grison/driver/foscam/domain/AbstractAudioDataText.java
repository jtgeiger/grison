package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract public class AbstractAudioDataText implements DatastreamI
{

    private long uptimeHundredths;      //INT32 (4 bytes; little endian); 10ms (Foscam docs call this field=timestamp)
    private long serialNumber;          //INT32 (4 bytes; little endian); increase from zero
    private long timestampSeconds;      //INT32 (4 bytes; little endian); From 1970.1.1 to current time (Foscam docs call this field=gatherTime)
    private AudioFormatE audioFormat;   //INT8; =0: adpcm
    //private long dataLength;          //INT32 (4 bytes; little endian); =160
    private byte[] dataContent;         //BINARY_STREAM[n]

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

    protected void parseImpl( byte[] data, int offset, int length )
    {
        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        this.uptimeHundredths = bb.getInt();
        this.serialNumber = bb.getInt();
        this.timestampSeconds = bb.getInt();

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

        bb.putInt( (int)uptimeHundredths );
        bb.putInt( (int)serialNumber );
        bb.putInt( (int)timestampSeconds );
        bb.put( (byte)audioFormat.getValue() );
        bb.putInt( dataContent.length );
        bb.put( dataContent );

        return bb.array();
    }

}
