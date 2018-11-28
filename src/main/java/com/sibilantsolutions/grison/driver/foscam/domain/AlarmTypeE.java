package com.sibilantsolutions.grison.driver.foscam.domain;


import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

public enum AlarmTypeE
{
    ALARM_STOP( Values.ALARM_STOP ),
    MOTION_DETECTION( Values.MOTION_DETECTION ),
    OUTSIDE_ALARM( Values.OUTSIDE_ALARM ),
    SOUND_DETECTION( Values.SOUND_DETECTION ),
    ;

    private interface Values
    {
        int ALARM_STOP = 0;
        int MOTION_DETECTION = 1;
        int OUTSIDE_ALARM = 2;
        int SOUND_DETECTION = 3;
    }

    public final FosInt8 value;

    AlarmTypeE(int value)
    {
        this.value = FosInt8.create(value);
    }

    public static AlarmTypeE fromValue(FosInt8 value)
    {
        switch (value.value())
        {
            case Values.ALARM_STOP:
                return ALARM_STOP;

            case Values.MOTION_DETECTION:
                return MOTION_DETECTION;

            case Values.OUTSIDE_ALARM:
                return OUTSIDE_ALARM;

            case Values.SOUND_DETECTION:
                return SOUND_DETECTION;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }

}
