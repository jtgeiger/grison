package com.sibilantsolutions.grison.driver.foscam.domain;

public enum OperationProtocolOpCodeE implements OpCodeI
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
        final static public short Login_Req           = 0;    //user -> IP camera
        final static public short Login_Resp          = 1;    //IP camera -> user
        final static public short Verify_Req          = 2;    //user -> IP camera
        final static public short Verify_Resp         = 3;    //ipcamera -> user
        final static public short Video_Start_Req     = 4;    //user -> ipcamera
        final static public short Video_Start_Resp    = 5;    //ipcamera -> user
        final static public short Video_End           = 6;    //user -> ipcamera
        final static public short Audio_Start_Req     = 8;    //user -> ipcamera
        final static public short Audio_Start_Resp    = 9;    //ipcamera -> user
        final static public short Audio_End           = 10;   //0x0A  //user -> ipcamera
        final static public short Talk_Start_Req      = 11;   //0x0B  //user -> ipcamera
        final static public short Talk_Start_Resp     = 12;   //0x0C  //ipcamera -> user
        final static public short Talk_End            = 13;   //0x0D  //user -> ipcamera
        final static public short UNK01               = 16;   //0x10  //user -> ipcamera
        final static public short UNK03               = 17;   //0x11  //ipcamera -> user
        final static public short Alarm_Notify        = 25;   //0x19  //ipcamera -> user
        final static public short UNK02               = 28;   //0x1C  //ipcamera -> user
        final static public short Keep_Alive          = 255;  //0xFF  //ipcamera <-> user
    }

    private short value;

    private OperationProtocolOpCodeE( short value )
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
//            case Login_Req:
//                return LoginReqText.parse( data, offset, length );

            case Login_Resp:
                return LoginRespText.parse( data, offset, length );

//            case Verify_Req:
//                return VerifyReqText.parse( data, offset, length );
//
            case Verify_Resp:
                return VerifyRespText.parse( data, offset, length );

            case Video_Start_Req:
                return VideoStartReqText.parse( data, offset, length );

            case Video_Start_Resp:
                return VideoStartRespText.parse( data, offset, length );
/*
            case Video_End:
                return Video_End.parse( data, offset, length );
*/
//            case Audio_Start_Req:
//                return AudioStartReqText.parse( data, offset, length );
//
            case Audio_Start_Resp:
                return AudioStartRespText.parse( data, offset, length );
/*
            case Audio_End:
                return Audio_End.parse( data, offset, length );
*/
            case Talk_Start_Req:
                return TalkStartReqText.parse( data, offset, length );

            case Talk_Start_Resp:
                return TalkStartRespText.parse( data, offset, length );
/*
            case Talk_End:
                return Talk_End.parse( data, offset, length );

            case UNK01:
                return UNK01.parse( data, offset, length );
*/
//            case UNK03:
//                return Unk03Text.parse( data, offset, length );

            case Alarm_Notify:
                return AlarmNotifyText.parse( data, offset, length );

            case UNK02:
                return Unk02Text.parse( data, offset, length );

            case Keep_Alive:
                return KeepAliveText.parse( data, offset, length );

            default:
                throw new IllegalArgumentException( "Unexpected value=" + this );
        }
    }

}
