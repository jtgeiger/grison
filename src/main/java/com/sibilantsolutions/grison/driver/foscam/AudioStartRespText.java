package com.sibilantsolutions.grison.driver.foscam;

import com.sibilantsolutions.grison.util.Convert;

public class AudioStartRespText implements DatastreamI
{

    private ResultCode resultCode;      //INT16 (2 bytes; little endian)
    private String dataConnectionId;    //INT32 (4 bytes; little endian)

    public ResultCode getResultCode()
    {
        return resultCode;
    }

    public void setResultCode( ResultCode resultCode )
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

    public static AudioStartRespText parse( String data )
    {
        AudioStartRespText text = new AudioStartRespText();

        int i = 0;

        int resultNum = (int)Convert.toNumLittleEndian( data.substring( i, i += 2 ) );
        text.resultCode = ResultCode.fromValue( resultNum );

        String id = data.substring( i, i += 4 );
        text.dataConnectionId = id;

        return text;
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
