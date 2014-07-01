package com.sibilantsolutions.grison.driver.foscam.domain;

import com.sibilantsolutions.grison.util.Convert;

abstract public class AbstractStartRespText implements DatastreamI
{

    private ResultCodeE resultCode;     //INT16 (2 bytes; little endian)
    private String dataConnectionId;    //INT32 (4 bytes; little endian)

    public ResultCodeE getResultCode()
    {
        return resultCode;
    }

    public void setResultCode( ResultCodeE resultCode )
    {
        this.resultCode = resultCode;
    }

    public String getDataConnectionId()
    {
        return dataConnectionId;
    }

    public void setDataConnectionId( String dataConnectionId )
    {
        this.dataConnectionId = dataConnectionId;
    }

    protected void parseImpl( String data )
    {
        int i = 0;

        int resultNum = (int)Convert.toNumLittleEndian( data.substring( i, i += 2 ) );
        this.resultCode = ResultCodeE.fromValue( resultNum );

        String id = data.substring( i, i += 4 );
        this.dataConnectionId = id;
    }

    @Override
    public String toDatastream()
    {
        StringBuilder buf = new StringBuilder( 2 + 4 );

        buf.append( Convert.toLittleEndian( resultCode.getValue(), 2 ) );
        buf.append( dataConnectionId );

        return buf.toString();
    }

}
