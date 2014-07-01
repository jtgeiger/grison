package com.sibilantsolutions.grison.driver.foscam.domain;

public class KeepAliveText implements DatastreamI
{

    public static KeepAliveText parse( String data )
    {
        KeepAliveText text = new KeepAliveText();

        //No-op.

        return text;
    }

    @Override
    public String toDatastream()
    {
        return "";
    }

}
