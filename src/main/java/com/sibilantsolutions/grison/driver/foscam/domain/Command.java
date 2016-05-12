package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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

    static public Command parse( byte[] data, int offset, int length )
    {
        Command c = new Command();

        ByteBuffer bb = ByteBuffer.wrap( data, offset, length );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        byte[] protocol = new byte[4];
        bb.get(protocol);
        c.protocol = ProtocolE.fromValue(protocol);

        short opCodeNum = bb.getShort();

        switch( c.protocol )
        {
            case OPERATION_PROTOCOL:
                c.opCode = OperationProtocolOpCodeE.fromValue( opCodeNum );
                break;

            case AUDIO_VIDEO_PROTOCOL:
                c.opCode = AudioVideoProtocolOpCodeE.fromValue( opCodeNum );
                break;

            case SEARCH_PROTOCOL:
                c.opCode = SearchProtocolOpCodeE.fromValue( opCodeNum );
                break;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + c.protocol );
        }

        bb.position( bb.position() + 1 );
        bb.position( bb.position() + 8 );

        int textLength = bb.getInt();

        bb.position( bb.position() + 4 );

        byte[] textBytes = new byte[textLength];
        bb.get( textBytes );

        c.commandText = c.opCode.parse( textBytes, 0, textBytes.length );

        return c;
    }

    @Override
    public byte[] toDatastream()
    {
        byte[] commandTextData = new byte[0];

        if ( commandText != null )
            commandTextData = commandText.toDatastream();

        ByteBuffer bb = ByteBuffer.allocate( 4 + 2 + 1 + 8 + 4 + 4 + commandTextData.length );
        bb.order( ByteOrder.LITTLE_ENDIAN );

        bb.put(protocol.getValue());
        bb.putShort( opCode.getValue() );

            //RESERVED
        bb.put( (byte)0x00 );

            //RESERVED
        for ( int i = 0; i < 8; i++ )
            bb.put( (byte)0x00 );

        bb.putInt( commandTextData.length );

            //RESERVED (seems to duplicate length)
        bb.putInt( commandTextData.length );

        bb.put( commandTextData );

        return bb.array();
    }

}
