package com.sibilantsolutions.grison.driver.foscam.domain;

import com.sibilantsolutions.grison.util.Convert;

public class InitRespText implements DatastreamI
{
    private ResultCodeE resultCode;     //INT16 (2 bytes; little endian)

    public ResultCodeE getResultCode()
    {
        return resultCode;
    }

    public void setResultCode( ResultCodeE resultCode )
    {
        this.resultCode = resultCode;
    }

    static public InitRespText parse( String data )
    {
        InitRespText text = new InitRespText();

        int i = 0;

        int resultNum = (int)Convert.toNumLittleEndian( data.substring( i, i += 2 ) );
        text.resultCode = ResultCodeE.fromValue( resultNum );

        return text;
    }

    @Override
    public String toDatastream()
    {
        StringBuilder buf = new StringBuilder( 2 );

        buf.append( Convert.toLittleEndian( resultCode.getValue(), 2 ) );

        return buf.toString();
    }

}
