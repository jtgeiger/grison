package com.sibilantsolutions.grison.driver.foscam.domain;

public interface OpCodeI
{

    public int getValue();

    public DatastreamI parse( String data );

}
