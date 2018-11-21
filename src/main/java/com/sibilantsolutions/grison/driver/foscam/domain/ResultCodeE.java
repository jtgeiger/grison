package com.sibilantsolutions.grison.driver.foscam.domain;

import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;

public enum ResultCodeE
{
    CORRECT( Values.CORRECT ),
    USER_WRONG( Values.USER_WRONG ),
    REFUSE_OVER_MAX_CONNS( Values.REFUSE_OVER_MAX_CONNS ),
    PWD_ERROR( Values.PWD_ERROR ),
    PRI_ERROR( Values.PRI_ERROR ),
    UNSUPPORTED( Values.UNSUPPORTED ),
    ;

    private interface Values
    {
        short CORRECT = 0;
        short USER_WRONG = 1;
        short REFUSE_OVER_MAX_CONNS = 2;
        short PWD_ERROR = 5;
        short PRI_ERROR = 6;
        short UNSUPPORTED = 7;
    }

    final public FosInt16 value;

    ResultCodeE(short value)
    {
        this.value = FosInt16.create(value);
    }

    public static ResultCodeE fromValue(FosInt16 value)
    {
        switch (value.value())
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
