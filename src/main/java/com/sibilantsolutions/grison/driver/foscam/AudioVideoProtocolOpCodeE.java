package com.sibilantsolutions.grison.driver.foscam;

public enum AudioVideoProtocolOpCodeE implements OpCodeI
{

    Login_Req( Values.Login_Req ),
    Video_Data( Values.Video_Data ),
    Audio_Data( Values.Audio_Data ),
    Talk_Data( Values.Talk_Data ),
    ;

    static private interface Values
    {
        final static public int Login_Req   = 0;    //user -> ipcamera
        final static public int Video_Data  = 1;    //ipcamera -> user
        final static public int Audio_Data  = 2;    //ipcamera -> user
        final static public int Talk_Data   = 3;    //user -> ipcamera
    }

    private int value;

    private AudioVideoProtocolOpCodeE( int value )
    {
        this.value = value;
    }

    public static AudioVideoProtocolOpCodeE fromValue( int value )
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
    public int getValue()
    {
        return value;
    }

    @Override
    public DatastreamI parse( String data )
    {
        switch ( this )
        {
            case Audio_Data:
                return AudioDataText.parse( data );

                //TODO

            default:
                throw new IllegalArgumentException( "Unexpected value=" + this );
        }
    }

}
