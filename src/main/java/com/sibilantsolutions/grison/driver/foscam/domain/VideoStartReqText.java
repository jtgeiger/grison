package com.sibilantsolutions.grison.driver.foscam.domain;

import com.sibilantsolutions.grison.util.Convert;

public class VideoStartReqText implements DatastreamI
{

    private int data;   //INT8

    public int getData()
    {
        return data;
    }

    public void setData( int data )
    {
        this.data = data;
    }

    public static VideoStartReqText parse( String data )
    {
        VideoStartReqText text = new VideoStartReqText();

        text.data = (int)Convert.toNumLittleEndian( data );

        return text;
    }

    @Override
    public String toDatastream()
    {
        return Convert.toLittleEndian( data, 1 );
    }

}
