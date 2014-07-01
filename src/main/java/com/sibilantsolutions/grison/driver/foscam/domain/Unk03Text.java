package com.sibilantsolutions.grison.driver.foscam.domain;

public class Unk03Text implements DatastreamI
{

    private String data;    //TODO: Parse

    public String getData()
    {
        return data;
    }

    public void setData( String data )
    {
        this.data = data;
    }

    @Override
    public String toDatastream()
    {
        return data;
    }

}
