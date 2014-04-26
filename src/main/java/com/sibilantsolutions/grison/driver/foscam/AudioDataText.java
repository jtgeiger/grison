package com.sibilantsolutions.grison.driver.foscam;

import com.sibilantsolutions.grison.util.Convert;

public class AudioDataText implements DatastreamI
{

    private long timestamp;             //INT32 (4 bytes; little endian); 10ms
    private long serialNumber;          //INT32 (4 bytes; little endian); increase from zero
    private long gatherTime;            //INT32 (4 bytes; little endian); From 1970.1.1 to current time
    private AudioFormat audioFormat;    //INT8; =0: adpcm
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

    public AudioFormat getAudioFormat()
    {
        return audioFormat;
    }

    public void setAudioFormat( AudioFormat audioFormat )
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

    public static AudioDataText parse( String data )
    {
        AudioDataText text = new AudioDataText();

        int i = 0;

        text.timestamp = Convert.toNumLittleEndian( data.substring( i, i += 4 ) );
        text.serialNumber = Convert.toNumLittleEndian( data.substring( i, i += 4 ) );
        text.gatherTime = Convert.toNumLittleEndian( data.substring( i, i += 4 ) );

        text.audioFormat = AudioFormat.fromValue( data.charAt( i++ ) );

        long dataLen = Convert.toNumLittleEndian( data.substring( i, i += 4 ) );

        String dc = data.substring( i, i += dataLen );
        text.dataContent = dc;

        return text;
    }

    @Override
    public String toDatastream()
    {
        StringBuilder buf = new StringBuilder( 177 );

        buf.append( Convert.toLittleEndian( timestamp, 4 ) );
        buf.append( Convert.toLittleEndian( serialNumber, 4 ) );
        buf.append( Convert.toLittleEndian( gatherTime, 4 ) );
        buf.append( Convert.toLittleEndian( audioFormat.getValue(), 1 ) );
        buf.append( Convert.toLittleEndian( dataContent.length(), 4 ) );
        buf.append( dataContent );

        return buf.toString();
    }

}
