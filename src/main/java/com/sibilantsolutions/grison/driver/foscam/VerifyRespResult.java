package com.sibilantsolutions.grison.driver.foscam;


public enum VerifyRespResult
{
    CORRECT( Values.CORRECT ),
    USER_WRONG( Values.USER_WRONG ),
    PWD_ERROR( Values.PWD_ERROR ),
    ;

    static private interface Values
    {
        final static public int CORRECT     = 0;
        final static public int USER_WRONG  = 1;
        final static public int PWD_ERROR   = 5;
    }

    private int value;

    private VerifyRespResult( int value )
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

}
