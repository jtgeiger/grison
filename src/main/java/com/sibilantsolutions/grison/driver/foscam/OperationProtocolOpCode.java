package com.sibilantsolutions.grison.driver.foscam;

public enum OperationProtocolOpCode implements OpCodeI
{

    Login_Req( Values.Login_Req ),
    Login_Resp( Values.Login_Resp ),
    ;

    static private interface Values
    {
        final static public int Login_Req   = 0;
        final static public int Login_Resp  = 1;
    }

    private int value;

    private OperationProtocolOpCode( int value )
    {
        this.value = value;
    }

    @Override
    public int getValue()
    {
        return value;
    }

}
