package com.sibilantsolutions.grison.driver.foscam.domain;

import com.sibilantsolutions.grison.util.Convert;

public class VerifyRespText implements DatastreamI
{

    private ResultCodeE resultCode;     //INT16 (2 bytes; little endian)
    //private int RESERVE               //INT8

    public ResultCodeE getResultCode()
    {
        return resultCode;
    }

    public void setResultCode( ResultCodeE resultCode )
    {
        this.resultCode = resultCode;
    }

    public static VerifyRespText parse( String data )
    {
        VerifyRespText text = new VerifyRespText();

        int i = 0;

        int resultCodeNum = (int)Convert.toNumLittleEndian( data.substring( i, i += 2 ) );

        text.resultCode = ResultCodeE.fromValue( resultCodeNum );

            //RESERVED
        i++;

        return text;
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
