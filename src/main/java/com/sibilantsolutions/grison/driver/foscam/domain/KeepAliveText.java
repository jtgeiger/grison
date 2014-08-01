package com.sibilantsolutions.grison.driver.foscam.domain;

public class KeepAliveText implements DatastreamI
{

    public static KeepAliveText parse( byte[] data, int offset, int length )
    {
        KeepAliveText text = new KeepAliveText();

        //No-op.

        return text;
    }

    @Override
    public byte[] toDatastream()
    {
        return new byte[0];
    }

}
