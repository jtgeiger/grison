package com.sibilantsolutions.grison.driver.foscam.domain;

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

    public static TalkStartReqText parse( byte[] data, int offset, int length )
    {
        TalkStartReqText text = new TalkStartReqText();

        text.data = data[offset];

        return text;
    }

    @Override
    public byte[] toDatastream()
    {
        return new byte[]{ (byte)data };
    }

}
