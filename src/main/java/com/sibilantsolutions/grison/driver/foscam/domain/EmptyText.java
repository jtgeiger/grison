package com.sibilantsolutions.grison.driver.foscam.domain;

public final class EmptyText implements DatastreamI
{

    @Override
    public byte[] toDatastream()
    {
        return new byte[0];
    }

}
