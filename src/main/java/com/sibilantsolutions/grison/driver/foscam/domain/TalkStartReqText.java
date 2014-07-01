package com.sibilantsolutions.grison.driver.foscam.domain;

import com.sibilantsolutions.grison.util.Convert;

public class TalkStartReqText implements DatastreamI
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

    public static TalkStartReqText parse( String data )
    {
        TalkStartReqText text = new TalkStartReqText();

        text.data = (int)Convert.toNumLittleEndian( data );

        return text;
    }

    @Override
    public String toDatastream()
    {
        return Convert.toLittleEndian( data, 1 );
    }

}
