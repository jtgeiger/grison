package com.sibilantsolutions.grison.driver.foscam.domain;

public enum OperationProtocolOpCodeE {

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

    private interface Values {
        short Login_Req           = 0;    //user -> IP camera
        short Login_Resp          = 1;    //IP camera -> user
        short Verify_Req          = 2;    //user -> IP camera
        short Verify_Resp         = 3;    //ipcamera -> user
        short Video_Start_Req     = 4;    //user -> ipcamera
        short Video_Start_Resp    = 5;    //ipcamera -> user
        short Video_End           = 6;    //user -> ipcamera
        short Audio_Start_Req     = 8;    //user -> ipcamera
        short Audio_Start_Resp    = 9;    //ipcamera -> user
        short Audio_End           = 10;   //0x0A  //user -> ipcamera
        short Talk_Start_Req      = 11;   //0x0B  //user -> ipcamera
        short Talk_Start_Resp     = 12;   //0x0C  //ipcamera -> user
        short Talk_End            = 13;   //0x0D  //user -> ipcamera
        short UNK01               = 16;   //0x10  //user -> ipcamera
        short UNK03               = 17;   //0x11  //ipcamera -> user
        short Alarm_Notify        = 25;   //0x19  //ipcamera -> user
        short UNK02               = 28;   //0x1C  //ipcamera -> user
        short Keep_Alive          = 255;  //0xFF  //ipcamera <-> user
    }

    private short value;

    OperationProtocolOpCodeE( short value )
    {
        this.value = value;
    }

    static public OperationProtocolOpCodeE fromValue( short value )
    {
        switch ( value )
        {
            case Values.Login_Req:
                return Login_Req;

            case Values.Login_Resp:
                return Login_Resp;

            case Values.Verify_Req:
                return Verify_Req;

            case Values.Verify_Resp:
                return Verify_Resp;

            case Values.Video_Start_Req:
                return Video_Start_Req;

            case Values.Video_Start_Resp:
                return Video_Start_Resp;

            case Values.Video_End:
                return Video_End;

            case Values.Audio_Start_Req:
                return Audio_Start_Req;

            case Values.Audio_Start_Resp:
                return Audio_Start_Resp;

            case Values.Audio_End:
                return Audio_End;

            case Values.Talk_Start_Req:
                return Talk_Start_Req;

            case Values.Talk_Start_Resp:
                return Talk_Start_Resp;

            case Values.Talk_End:
                return Talk_End;

            case Values.UNK01:
                return UNK01;

            case Values.UNK03:
                return UNK03;

            case Values.Alarm_Notify:
                return Alarm_Notify;

            case Values.UNK02:
                return UNK02;

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
