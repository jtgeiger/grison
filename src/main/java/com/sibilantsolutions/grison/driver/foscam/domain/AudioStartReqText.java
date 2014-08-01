package com.sibilantsolutions.grison.driver.foscam.domain;

public class AudioStartReqText implements DatastreamI
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

    @Override
    public byte[] toDatastream()
    {
        return new byte[] { (byte)data };
    }

}
