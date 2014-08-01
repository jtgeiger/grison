package com.sibilantsolutions.grison.driver.foscam.domain;

import com.sibilantsolutions.grison.util.Convert;

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
    public byte[] toDatastream()
    {
        return data.getBytes( Convert.cs );
    }

}
