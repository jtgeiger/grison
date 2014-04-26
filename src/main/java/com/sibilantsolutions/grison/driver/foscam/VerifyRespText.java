package com.sibilantsolutions.grison.driver.foscam;

import com.sibilantsolutions.grison.util.Convert;

public class VerifyRespText implements DatastreamI
{

    private ResultCode resultCode;    //INT16 (2 bytes; little endian)
    //private int RESERVE               //INT8

    public ResultCode getResultCode()
    {
        return resultCode;
    }

    public void setResultCode( ResultCode resultCode )
    {
        this.resultCode = resultCode;
    }

    @Override
    public String toDatastream()
    {
        StringBuilder buf = new StringBuilder( 2 + 1 );

        buf.append( Convert.toLittleEndian( resultCode.getValue(), 2 ) );

            //RESERVED
        buf.append( (char)0 );

        return buf.toString();
    }

}
