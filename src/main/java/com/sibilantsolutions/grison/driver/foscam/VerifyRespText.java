package com.sibilantsolutions.grison.driver.foscam;

import com.sibilantsolutions.grison.util.Convert;

public class VerifyRespText implements DatastreamI
{

    private VerifyRespResult result;    //INT16 (2 bytes; little endian)
    //private int RESERVE               //INT8

    public VerifyRespResult getResult()
    {
        return result;
    }

    public void setResult( VerifyRespResult result )
    {
        this.result = result;
    }

    @Override
    public String toDatastream()
    {
        StringBuilder buf = new StringBuilder( 2 + 1 );

        buf.append( Convert.toLittleEndian( result.getValue(), 2 ) );

            //RESERVED
        buf.append( (char)0 );

        return buf.toString();
    }

}
