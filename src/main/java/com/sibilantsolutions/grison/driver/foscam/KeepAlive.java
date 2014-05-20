package com.sibilantsolutions.grison.driver.foscam;

public class KeepAlive implements DatastreamI
{


    public static KeepAlive parse( String data )
    {
        KeepAlive text = new KeepAlive();

        //No-op.

        return text;
    }

    @Override
    public String toDatastream()
    {
        return "";
    }

}
