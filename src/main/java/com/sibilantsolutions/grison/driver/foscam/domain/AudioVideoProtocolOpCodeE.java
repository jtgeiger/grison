package com.sibilantsolutions.grison.driver.foscam.domain;

public enum AudioVideoProtocolOpCodeE implements OpCodeI
{

    Login_Req( Values.Login_Req ),
    Video_Data( Values.Video_Data ),
    Audio_Data( Values.Audio_Data ),
    Talk_Data( Values.Talk_Data ),
    ;

    static private interface Values
    {
        final static public short Login_Req   = 0;  //user -> ipcamera
        final static public short Video_Data  = 1;  //ipcamera -> user
        final static public short Audio_Data  = 2;  //ipcamera -> user
        final static public short Talk_Data   = 3;  //user -> ipcamera
    }

    private short value;

    private AudioVideoProtocolOpCodeE( short value )
    {
        this.value = value;
    }

    public static AudioVideoProtocolOpCodeE fromValue( short value )
    {
        switch ( value )
        {
            case Values.Login_Req:
                return Login_Req;

            case Values.Video_Data:
                return Video_Data;

            case Values.Audio_Data:
                return Audio_Data;

            case Values.Talk_Data:
                return Talk_Data;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }

    @Override
    public short getValue()
    {
        return value;
    }

    @Override
    public DatastreamI parse( byte[] data, int offset, int length )
    {
        switch ( this )
        {
            case Video_Data:
                return VideoDataText.parse( data, offset, length );

            case Audio_Data:
                return AudioDataText.parse( data, offset, length );

                //TODO

            default:
                throw new IllegalArgumentException( "Unexpected value=" + this );
        }
    }

}
