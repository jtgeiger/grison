package com.sibilantsolutions.grison.driver.foscam.domain;

import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

public enum AudioFormatE
{
    ADPCM( Values.ADPCM ),
    ;

    private interface Values
    {
        int ADPCM = 0;
    }

    public final FosInt8 value;

    AudioFormatE(int value)
    {
        this.value = FosInt8.create(value);
    }

    public static AudioFormatE fromValue(FosInt8 value)
    {
        switch (value.value())
        {
            case Values.ADPCM:
                return ADPCM;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }

}
