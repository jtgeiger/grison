package com.sibilantsolutions.grison.driver.foscam.domain;


public enum AlarmTypeE
{
    ALARM_STOP( Values.ALARM_STOP ),
    MOTION_DETECTION( Values.MOTION_DETECTION ),
    OUTSIDE_ALARM( Values.OUTSIDE_ALARM ),
    SOUND_DETECTION( Values.SOUND_DETECTION ),
    ;

    static private interface Values
    {
        final static public int ALARM_STOP          = 0;
        final static public int MOTION_DETECTION    = 1;
        final static public int OUTSIDE_ALARM       = 2;
        final static public int SOUND_DETECTION     = 3;
    }

    final private int value;

    private AlarmTypeE( int value )
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public static AlarmTypeE fromValue( int value )
    {
        switch ( value )
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
