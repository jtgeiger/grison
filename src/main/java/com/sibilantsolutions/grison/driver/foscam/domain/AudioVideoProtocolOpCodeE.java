package com.sibilantsolutions.grison.driver.foscam.domain;

public enum AudioVideoProtocolOpCodeE {

    Login_Req( Values.Login_Req ),
    Video_Data( Values.Video_Data ),
    Audio_Data( Values.Audio_Data ),
    Talk_Data( Values.Talk_Data ),
    Keep_Alive( Values.Keep_Alive ),
    ;

    private interface Values
    {
        short Login_Req     = 0;    //user -> ipcamera
        short Video_Data    = 1;    //ipcamera -> user
        short Audio_Data    = 2;    //ipcamera -> user
        short Talk_Data     = 3;    //user -> ipcamera
        short Keep_Alive    = 255;  //user <-> ipcamera
    }

    private short value;

    AudioVideoProtocolOpCodeE( short value )
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

            case Values.Keep_Alive:
                return Keep_Alive;

            default:
                throw new IllegalArgumentException( "Unexpected value=" + value );
        }
    }

    public short getValue()
    {
        return value;
    }

}
