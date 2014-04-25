package com.sibilantsolutions.grison.driver.foscam;

import com.sibilantsolutions.grison.util.Convert;

public class Command implements DatastreamI
{

    private Protocol protocol;  //BINARY_STREAM[4]
    private OpCodeI opCode;     //INT16 (2 bytes; little endian)
    //private RESERVE=0         //INT8 (1 byte)
    //private RESERVE           //BINARY_STREAM[8]
    //private int textLength;   //INT32 (4 bytes; little endian)
    //private RESERVE           //INT32 (4 bytes; little endian)
    private DatastreamI commandText;   //BINARY_STREAM[n]

    public Protocol getProtocol()
    {
        return protocol;
    }

    public void setProtocol( Protocol protocol )
    {
        this.protocol = protocol;
    }

    public OpCodeI getOpCode()
    {
        return opCode;
    }

    public void setOpCode( OpCodeI opCode )
    {
        this.opCode = opCode;
    }

    public DatastreamI getCommandText()
    {
        return commandText;
    }

    public void setCommandText( DatastreamI commandText )
    {
        this.commandText = commandText;
    }

    @Override
    public String toDatastream()
    {
        StringBuilder buf = new StringBuilder();

        buf.append( protocol.getValue() );
        buf.append( Convert.toLittleEndian( opCode.getValue(), 2 ) );

            //RESERVED
        buf.append( (char)0x00 );

            //RESERVED
        for ( int i = 0; i < 8; i++ )
            buf.append( (char)0x00 );

        String commandTextStr = commandText.toDatastream();

        buf.append( Convert.toLittleEndian( commandTextStr.length(), 4 ) );

            //RESERVED
        //for ( int i = 0; i < 4; i++ )
        //    buf.append( (char)0x00 );
        buf.append( Convert.toLittleEndian( commandTextStr.length(), 4 ) );

        buf.append( commandTextStr );

        return buf.toString();
    }

}