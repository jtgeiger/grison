package com.sibilantsolutions.grison.driver.foscam;

import com.sibilantsolutions.grison.util.Convert;

public class Command implements DatastreamI
{

    private ProtocolE protocol; //BINARY_STREAM[4]
    private OpCodeI opCode;     //INT16 (2 bytes; little endian)
    //private RESERVE=0         //INT8 (1 byte)
    //private RESERVE           //BINARY_STREAM[8]
    //private int textLength;   //INT32 (4 bytes; little endian)
    //private RESERVE           //INT32 (4 bytes; little endian)
    private DatastreamI commandText;   //BINARY_STREAM[n]

    public ProtocolE getProtocol()
    {
        return protocol;
    }

    public void setProtocol( ProtocolE protocol )
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

    static public Command parse( String data )
    {
        Command c = new Command();

        int i = 0;

        c.protocol = ProtocolE.fromValue( data.substring( i, i += 4 ) );

        int opCodeNum = (int)Convert.toNumLittleEndian( data.substring( i, i += 2 ) );

        switch( c.protocol )
        {
            case OPERATION_PROTOCOL:
                c.opCode = OperationProtocolOpCodeE.fromValue( opCodeNum );
                break;

            case AUDIO_VIDEO_PROTOCOL:
                c.opCode = AudioVideoProtocolOpCodeE.fromValue( opCodeNum );
                break;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + c.protocol );
        }

        i += 1;
        i += 8;

        int textLength = (int)Convert.toNumLittleEndian( data.substring( i, i += 4 ) );

        i += 4;

        String textStr = data.substring( i, i += textLength );

        c.commandText = c.opCode.parse( textStr );

        return c;
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

        String commandTextStr = "";

        if ( commandText != null )
            commandTextStr = commandText.toDatastream();

        buf.append( Convert.toLittleEndian( commandTextStr.length(), 4 ) );

            //RESERVED
        //for ( int i = 0; i < 4; i++ )
        //    buf.append( (char)0x00 );
        buf.append( Convert.toLittleEndian( commandTextStr.length(), 4 ) );

        buf.append( commandTextStr );

        return buf.toString();
    }

}
