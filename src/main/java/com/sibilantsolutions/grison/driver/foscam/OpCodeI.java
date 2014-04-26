package com.sibilantsolutions.grison.driver.foscam;

public interface OpCodeI
{

    public int getValue();

    public DatastreamI parse( String data );

}
