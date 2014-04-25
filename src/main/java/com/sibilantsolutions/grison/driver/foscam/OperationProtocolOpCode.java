package com.sibilantsolutions.grison.driver.foscam;

public enum OperationProtocolOpCode implements OpCodeI
{

    Login_Req( Values.Login_Req ),
    Login_Resp( Values.Login_Resp ),
    Verify_Req( Values.Verify_Req ),
    Verify_Resp( Values.Verify_Resp ),
    Video_Start_Req( Values.Video_Start_Req ),
    Video_Start_Resp( Values.Video_Start_Resp ),
    Video_End( Values.Video_End ),
    Audio_Start_Req( Values.Audio_Start_Req ),
    Audio_Start_Resp( Values.Audio_Start_Resp ),
    Audio_End( Values.Audio_End ),
    Talk_Start_Req( Values.Talk_Start_Req ),
    Talk_Start_Resp( Values.Talk_Start_Resp ),
    Talk_End( Values.Talk_End ),
    UNK01( Values.UNK01 ),
    UNK03( Values.UNK03 ),
    Alarm_Notify( Values.Alarm_Notify ),
    UNK02( Values.UNK02 ),
    Keep_Alive( Values.Keep_Alive ),
    ;

    static private interface Values
    {
        final static public int Login_Req           = 0;    //user -> IP camera
        final static public int Login_Resp          = 1;    //IP camera -> user
        final static public int Verify_Req          = 2;    //user -> IP camera
        final static public int Verify_Resp         = 3;    //ipcamera -> user
        final static public int Video_Start_Req     = 4;    //user -> ipcamera
        final static public int Video_Start_Resp    = 5;    //ipcamera -> user
        final static public int Video_End           = 6;    //user -> ipcamera
        final static public int Audio_Start_Req     = 8;    //user -> ipcamera
        final static public int Audio_Start_Resp    = 9;    //ipcamera -> user
        final static public int Audio_End           = 10;   //user -> ipcamera
        final static public int Talk_Start_Req      = 11;   //user -> ipcamera
        final static public int Talk_Start_Resp     = 12;   //ipcamera -> user
        final static public int Talk_End            = 13;   //user -> ipcamera
        final static public int UNK01               = 16;   //user -> ipcamera
        final static public int UNK03               = 17;   //ipcamera -> user
        final static public int Alarm_Notify        = 25;   //ipcamera -> user
        final static public int UNK02               = 28;   //ipcamera -> user
        final static public int Keep_Alive          = 255;  //ipcamera <-> user
    }

    private int value;

    private OperationProtocolOpCode( int value )
    {
        this.value = value;
    }

    @Override
    public int getValue()
    {
        return value;
    }

}
