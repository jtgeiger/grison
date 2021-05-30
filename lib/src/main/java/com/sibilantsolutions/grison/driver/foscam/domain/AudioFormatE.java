package com.sibilantsolutions.grison.driver.foscam.domain;

import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

public enum AudioFormatE
{
    ADPCM( Values.ADPCM ),
    ;

    private interface Values
    {
        byte ADPCM = 0;
    }

    public final FosInt8 value;

    AudioFormatE(byte value)
    {
        this.value = FosInt8.create(value);
    }

    public static AudioFormatE fromValue(FosInt8 value)
    {
        switch (value.toByte())
        {
            case Values.ADPCM:
                return ADPCM;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }

}
