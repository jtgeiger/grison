package com.sibilantsolutions.grison.driver.foscam.domain;

public enum AudioFormatE
{
    ADPCM( Values.ADPCM ),
    ;

    static private interface Values
    {
        final static public int ADPCM   = 0;
    }

    private int value;

    private AudioFormatE( int value )
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public static AudioFormatE fromValue( char value )
    {
        switch ( value )
        {
            case Values.ADPCM:
                return ADPCM;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }

}
