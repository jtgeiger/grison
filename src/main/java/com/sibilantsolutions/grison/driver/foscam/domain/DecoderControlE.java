package com.sibilantsolutions.grison.driver.foscam.domain;

public enum DecoderControlE
{
    UP( Values.UP ),
    STOP_UP( Values.STOP_UP ),
    DOWN( Values.DOWN ),
    STOP_DOWN( Values.STOP_DOWN ),
    LEFT( Values.LEFT ),
    STOP_LEFT( Values.STOP_LEFT ),
    RIGHT( Values.RIGHT ),
    STOP_RIGHT( Values.STOP_RIGHT ),
    SMALL_APERATURE( Values.SMALL_APERATURE ),
    STOP_SMALL_APERATURE( Values.STOP_SMALL_APERATURE ),
    LARGE_APERATURE( Values.LARGE_APERATURE ),
    STOP_LARGE_APERATURE( Values.STOP_LARGE_APERATURE ),
    FOCUS_CLOSE( Values.FOCUS_CLOSE ),
    STOP_FOCUS_CLOSE( Values.STOP_FOCUS_CLOSE ),
    FOCUS_FAR( Values.FOCUS_FAR ),
    STOP_FOCUS_FAR( Values.STOP_FOCUS_FAR ),
    ZOOM_CLOSE( Values.ZOOM_CLOSE ),
    STOP_ZOOM_CLOSE( Values.STOP_ZOOM_CLOSE ),
    ZOOM_FAR( Values.ZOOM_FAR ),
    STOP_ZOOM_FAR( Values.STOP_ZOOM_FAR ),
    AUTO_PATROL( Values.AUTO_PATROL ),
    STOP_AUTO_PATROL( Values.STOP_AUTO_PATROL ),
    CLOSE_SWITCH_1( Values.CLOSE_SWITCH_1 ),
    DISCONNECT_SWITCH_1( Values.DISCONNECT_SWITCH_1 ),
    CLOSE_SWITCH_2( Values.CLOSE_SWITCH_2 ),
    DISCONNECT_SWITCH_2( Values.DISCONNECT_SWITCH_2 ),
    CLOSE_SWITCH_3( Values.CLOSE_SWITCH_3 ),
    DISCONNECT_SWITCH_3( Values.DISCONNECT_SWITCH_3 ),
    CLOSE_SWITCH_4( Values.CLOSE_SWITCH_4 ),
    DISCONNECT_SWITCH_4( Values.DISCONNECT_SWITCH_4 ),
    SET_PRESET_1( Values.SET_PRESET_1 ),
    GO_TO_PRESET_1( Values.GO_TO_PRESET_1 ),
    SET_PRESET_2( Values.SET_PRESET_2 ),
    GO_TO_PRESET_2( Values.GO_TO_PRESET_2 ),
    SET_PRESET_3( Values.SET_PRESET_3 ),
    GO_TO_PRESET_3( Values.GO_TO_PRESET_3 ),
    SET_PRESET_4( Values.SET_PRESET_4 ),
    GO_TO_PRESET_4( Values.GO_TO_PRESET_4 ),
    SET_PRESET_5( Values.SET_PRESET_5 ),
    GO_TO_PRESET_5( Values.GO_TO_PRESET_5 ),
    SET_PRESET_6( Values.SET_PRESET_6 ),
    GO_TO_PRESET_6( Values.GO_TO_PRESET_6 ),
    SET_PRESET_7( Values.SET_PRESET_7 ),
    GO_TO_PRESET_7( Values.GO_TO_PRESET_7 ),
    SET_PRESET_8( Values.SET_PRESET_8 ),
    GO_TO_PRESET_8( Values.GO_TO_PRESET_8 ),
    SET_PRESET_9( Values.SET_PRESET_9 ),
    GO_TO_PRESET_9( Values.GO_TO_PRESET_9 ),
    SET_PRESET_10( Values.SET_PRESET_10 ),
    GO_TO_PRESET_10( Values.GO_TO_PRESET_10 ),
    SET_PRESET_11( Values.SET_PRESET_11 ),
    GO_TO_PRESET_11( Values.GO_TO_PRESET_11 ),
    SET_PRESET_12( Values.SET_PRESET_12 ),
    GO_TO_PRESET_12( Values.GO_TO_PRESET_12 ),
    SET_PRESET_13( Values.SET_PRESET_13 ),
    GO_TO_PRESET_13( Values.GO_TO_PRESET_13 ),
    SET_PRESET_14( Values.SET_PRESET_14 ),
    GO_TO_PRESET_14( Values.GO_TO_PRESET_14 ),
    SET_PRESET_15( Values.SET_PRESET_15 ),
    GO_TO_PRESET_15( Values.GO_TO_PRESET_15 ),
    SET_PRESET_16( Values.SET_PRESET_16 ),
    GO_TO_PRESET_16( Values.GO_TO_PRESET_16 ),
    //...
    IO_OUTPUT_HIGH( Values.IO_OUTPUT_HIGH ),
    IO_OUTPUT_LOW( Values.IO_OUTPUT_LOW ),
    MOTOR_TEST_MODE( Values.MOTOR_TEST_MODE ),
    ;

    static private interface Values
    {

        int UP                      = 0;
        int STOP_UP                 = 1;
        int DOWN                    = 2;
        int STOP_DOWN               = 3;
        int LEFT                    = 4;
        int STOP_LEFT               = 5;
        int RIGHT                   = 6;
        int STOP_RIGHT              = 7;
        int SMALL_APERATURE         = 8;
        int STOP_SMALL_APERATURE    = 9;
        int LARGE_APERATURE         = 10;
        int STOP_LARGE_APERATURE    = 11;
        int FOCUS_CLOSE             = 12;
        int STOP_FOCUS_CLOSE        = 13;
        int FOCUS_FAR               = 14;
        int STOP_FOCUS_FAR          = 15;
        int ZOOM_CLOSE              = 16;
        int STOP_ZOOM_CLOSE         = 17;
        int ZOOM_FAR                = 18;
        int STOP_ZOOM_FAR           = 19;
        int AUTO_PATROL             = 20;
        int STOP_AUTO_PATROL        = 21;
        int CLOSE_SWITCH_1          = 22;
        int DISCONNECT_SWITCH_1     = 23;
        int CLOSE_SWITCH_2          = 24;
        int DISCONNECT_SWITCH_2     = 25;
        int CLOSE_SWITCH_3          = 26;
        int DISCONNECT_SWITCH_3     = 27;
        int CLOSE_SWITCH_4          = 28;
        int DISCONNECT_SWITCH_4     = 29;
        int SET_PRESET_1            = 30;
        int GO_TO_PRESET_1          = 31;
        int SET_PRESET_2            = 32;
        int GO_TO_PRESET_2          = 33;
        int SET_PRESET_3            = 34;
        int GO_TO_PRESET_3          = 35;
        int SET_PRESET_4            = 36;
        int GO_TO_PRESET_4          = 37;
        int SET_PRESET_5            = 38;
        int GO_TO_PRESET_5          = 39;
        int SET_PRESET_6            = 40;
        int GO_TO_PRESET_6          = 41;
        int SET_PRESET_7            = 42;
        int GO_TO_PRESET_7          = 43;
        int SET_PRESET_8            = 44;
        int GO_TO_PRESET_8          = 45;
        int SET_PRESET_9            = 46;
        int GO_TO_PRESET_9          = 47;
        int SET_PRESET_10           = 48;
        int GO_TO_PRESET_10         = 49;
        int SET_PRESET_11           = 50;
        int GO_TO_PRESET_11         = 51;
        int SET_PRESET_12           = 52;
        int GO_TO_PRESET_12         = 53;
        int SET_PRESET_13           = 54;
        int GO_TO_PRESET_13         = 55;
        int SET_PRESET_14           = 56;
        int GO_TO_PRESET_14         = 57;
        int SET_PRESET_15           = 58;
        int GO_TO_PRESET_15         = 59;
        int SET_PRESET_16           = 60;
        int GO_TO_PRESET_16         = 61;
        //.... up to preset 32 (=92/93)??
        int IO_OUTPUT_HIGH = 94;   //Night vision off
        int IO_OUTPUT_LOW = 95;   //Night vision auto
        int MOTOR_TEST_MODE         = 255;
    }

    private int value;

    private DecoderControlE( int value )
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

}
