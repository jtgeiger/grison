package com.sibilantsolutions.grison.driver.foscam;


public enum Protocol
{
    OPERATION_PROTOCOL( Values.OPERATION_PROTOCOL ),
    AUDIO_VIDEO_PROTOCOL( Values.AUDIO_VIDEO_PROTOCOL );

    static private interface Values
    {
        final static public String OPERATION_PROTOCOL      = "MO_O";
        final static public String AUDIO_VIDEO_PROTOCOL    = "MO_V";
    }

    private String value;

    private Protocol( String value )
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

}
