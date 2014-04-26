package com.sibilantsolutions.grison.driver.foscam;

public enum AudioFormat
{
    ADPCM( Values.ADPCM ),
    ;

    static private interface Values
    {
        final static public int ADPCM   = 0;
    }

    private int value;

    private AudioFormat( int value )
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

}
