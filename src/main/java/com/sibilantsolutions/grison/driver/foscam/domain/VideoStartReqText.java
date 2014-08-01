package com.sibilantsolutions.grison.driver.foscam.domain;

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

    public static VideoStartReqText parse( byte[] data, int offset, int length )
    {
        VideoStartReqText text = new VideoStartReqText();

        text.data = data[offset];

        return text;
    }

    @Override
    public byte[] toDatastream()
    {
        return new byte[] { (byte)data };
    }

}
