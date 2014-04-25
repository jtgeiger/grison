package com.sibilantsolutions.grison.driver.foscam;

public enum AudioVideoProtocolOpCode implements OpCodeI
{

    ;

    static private interface Values
    {

    }

    private int value;

    private AudioVideoProtocolOpCode( int value )
    {
        this.value = value;
    }

    @Override
    public int getValue()
    {
        return value;
    }

}
