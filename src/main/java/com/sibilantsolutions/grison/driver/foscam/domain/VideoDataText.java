package com.sibilantsolutions.grison.driver.foscam.domain;

import com.sibilantsolutions.grison.util.Convert;

public class VideoDataText implements DatastreamI
{

    private long timestamp;             //INT32 (4 bytes; little endian);
    private long framesPerSec;          //INT32 (4 bytes; little endian); From 1970.1.1 to current time
    //private RESERVE                   //INT8
    //private long dataLength;          //INT32 (4 bytes; little endian);
    private String dataContent;         //BINARY_STREAM[n]

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

    public String getDataContent()
    {
        return dataContent;
    }

    public void setDataContent( String dataContent )
    {
        this.dataContent = dataContent;
    }

    public static VideoDataText parse( String data )
    {
        VideoDataText text = new VideoDataText();

        int i = 0;

        text.timestamp = Convert.toNumLittleEndian( data.substring( i, i += 4 ) );
        text.framesPerSec = Convert.toNumLittleEndian( data.substring( i, i += 4 ) );

        i++;    //RESERVE

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
        buf.append( Convert.toLittleEndian( framesPerSec, 4 ) );
        buf.append( (char)0 );    //RESERVE
        buf.append( Convert.toLittleEndian( dataContent.length(), 4 ) );
        buf.append( dataContent );

        return buf.toString();
    }

}
