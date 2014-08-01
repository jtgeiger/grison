package com.sibilantsolutions.grison.driver.foscam.domain;

public interface OpCodeI
{

    public short getValue();

    public DatastreamI parse( byte[] data, int offset, int length );

}
