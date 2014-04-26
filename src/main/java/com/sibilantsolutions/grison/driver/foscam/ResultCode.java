package com.sibilantsolutions.grison.driver.foscam;

public enum ResultCode
{
    CORRECT( Values.CORRECT ),
    USER_WRONG( Values.USER_WRONG ),
    REFUSE_OVER_MAX_CONNS( Values.REFUSE_OVER_MAX_CONNS ),
    PWD_ERROR( Values.PWD_ERROR ),
    UNSUPPORTED( Values.UNSUPPORTED ),
    ;

    static private interface Values
    {
        final static public int CORRECT                 = 0;
        final static public int USER_WRONG              = 1;
        final static public int REFUSE_OVER_MAX_CONNS   = 2;
        final static public int PWD_ERROR               = 5;
        final static public int UNSUPPORTED             = 7;
    }

    private int value;

    private ResultCode( int value )
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

}
