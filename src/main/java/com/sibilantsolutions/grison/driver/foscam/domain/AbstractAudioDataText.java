package com.sibilantsolutions.grison.driver.foscam.domain;

import com.sibilantsolutions.grison.util.Convert;

abstract public class AbstractAudioDataText implements DatastreamI
{

    private long timestamp;             //INT32 (4 bytes; little endian); 10ms
    private long serialNumber;          //INT32 (4 bytes; little endian); increase from zero
    private long gatherTime;            //INT32 (4 bytes; little endian); From 1970.1.1 to current time
    private AudioFormatE audioFormat;   //INT8; =0: adpcm
    //private long dataLength;          //INT32 (4 bytes; little endian); =160
    private String dataContent;         //BINARY_STREAM[n]

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

    public String getDataContent()
    {
        return dataContent;
    }

    public void setDataContent( String dataContent )
    {
        this.dataContent = dataContent;
    }

    protected void parseImpl( String data )
    {
        int i = 0;

        this.timestamp = Convert.toNumLittleEndian( data.substring( i, i += 4 ) );
        this.serialNumber = Convert.toNumLittleEndian( data.substring( i, i += 4 ) );
        this.gatherTime = Convert.toNumLittleEndian( data.substring( i, i += 4 ) );

        this.audioFormat = AudioFormatE.fromValue( data.charAt( i++ ) );

        long dataLen = Convert.toNumLittleEndian( data.substring( i, i += 4 ) );

        String dc = data.substring( i, i += dataLen );
        this.dataContent = dc;
    }

    @Override
    public String toDatastream()
    {
        StringBuilder buf = new StringBuilder( 4 + 4 + 4 + 1 + 4 + dataContent.length() );  //177

        buf.append( Convert.toLittleEndian( timestamp, 4 ) );
        buf.append( Convert.toLittleEndian( serialNumber, 4 ) );
        buf.append( Convert.toLittleEndian( gatherTime, 4 ) );
        buf.append( Convert.toLittleEndian( audioFormat.getValue(), 1 ) );
        buf.append( Convert.toLittleEndian( dataContent.length(), 4 ) );
        buf.append( dataContent );

        return buf.toString();
    }

}
