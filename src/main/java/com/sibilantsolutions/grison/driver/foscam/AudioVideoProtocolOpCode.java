package com.sibilantsolutions.grison.driver.foscam;

public enum AudioVideoProtocolOpCode implements OpCodeI
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

    private AudioVideoProtocolOpCode( int value )
    {
        this.value = value;
    }

    @Override
    public int getValue()
    {
        return value;
    }

}
