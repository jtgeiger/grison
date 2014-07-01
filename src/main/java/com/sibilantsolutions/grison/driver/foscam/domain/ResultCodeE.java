package com.sibilantsolutions.grison.driver.foscam.domain;

public enum ResultCodeE
{
    CORRECT( Values.CORRECT ),
    USER_WRONG( Values.USER_WRONG ),
    REFUSE_OVER_MAX_CONNS( Values.REFUSE_OVER_MAX_CONNS ),
    PWD_ERROR( Values.PWD_ERROR ),
    PRI_ERROR( Values.PRI_ERROR ),
    UNSUPPORTED( Values.UNSUPPORTED ),
    ;

    static private interface Values
    {
        final static public int CORRECT                 = 0;
        final static public int USER_WRONG              = 1;
        final static public int REFUSE_OVER_MAX_CONNS   = 2;
        final static public int PWD_ERROR               = 5;
        final static public int PRI_ERROR               = 6;
        final static public int UNSUPPORTED             = 7;
    }

    final private int value;

    private ResultCodeE( int value )
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public static ResultCodeE fromValue( int value )
    {
        switch ( value )
        {
            case Values.CORRECT:
                return CORRECT;

            case Values.USER_WRONG:
                return USER_WRONG;

            case Values.REFUSE_OVER_MAX_CONNS:
                return REFUSE_OVER_MAX_CONNS;

            case Values.PWD_ERROR:
                return PWD_ERROR;

            case Values.PRI_ERROR:
                return PRI_ERROR;

            case Values.UNSUPPORTED:
                return UNSUPPORTED;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }

}
