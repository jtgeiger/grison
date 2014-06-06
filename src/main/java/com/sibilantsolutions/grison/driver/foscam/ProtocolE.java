package com.sibilantsolutions.grison.driver.foscam;

public enum ProtocolE
{
    OPERATION_PROTOCOL( Values.OPERATION_PROTOCOL ),
    AUDIO_VIDEO_PROTOCOL( Values.AUDIO_VIDEO_PROTOCOL ),
    SEARCH_PROTOCOL( Values.SEARCH_PROTOCOL ),
    ;

    static private interface Values
    {
        final static public String OPERATION_PROTOCOL      = "MO_O";
        final static public String AUDIO_VIDEO_PROTOCOL    = "MO_V";
        final static public String SEARCH_PROTOCOL         = "MO_I";
    }

    private String value;

    private ProtocolE( String value )
    {
        this.value = value;
    }

    public static ProtocolE fromValue( String value )
    {
        switch ( value )
        {
            case Values.OPERATION_PROTOCOL:
                return OPERATION_PROTOCOL;

            case Values.AUDIO_VIDEO_PROTOCOL:
                return AUDIO_VIDEO_PROTOCOL;

            case Values.SEARCH_PROTOCOL:
                return SEARCH_PROTOCOL;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }

    public String getValue()
    {
        return value;
    }

}
