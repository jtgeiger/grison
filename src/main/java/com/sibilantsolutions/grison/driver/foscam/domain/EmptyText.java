package com.sibilantsolutions.grison.driver.foscam.domain;

public final class EmptyText implements DatastreamI
{

    @Override
    public String toDatastream()
    {
        return "";
    }

}
