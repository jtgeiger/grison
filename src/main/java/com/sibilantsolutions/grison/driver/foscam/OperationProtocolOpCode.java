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

    static public OperationProtocolOpCode fromValue( int value )
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
    public int getValue()
    {
        return value;
    }

    @Override
    public DatastreamI parse( String data )
    {
        switch ( this )
        {
//            case Login_Req:
//                return LoginReqText.parse( data );

            case Login_Resp:
                return LoginRespText.parse( data );

//            case Verify_Req:
//                return VerifyReqText.parse( data );
//
//            case Verify_Resp:
//                return VerifyRespText.parse( data );
/*
            case Video_Start_Req:
                return Video_Start_Req.parse( data );

            case Video_Start_Resp:
                return Video_Start_Resp.parse( data );

            case Video_End:
                return Video_End.parse( data );
*/
//            case Audio_Start_Req:
//                return AudioStartReqText.parse( data );
//
            case Audio_Start_Resp:
                return AudioStartRespText.parse( data );
/*
            case Audio_End:
                return Audio_End.parse( data );

            case Talk_Start_Req:
                return Talk_Start_Req.parse( data );

            case Talk_Start_Resp:
                return Talk_Start_Resp.parse( data );

            case Talk_End:
                return Talk_End.parse( data );

            case UNK01:
                return UNK01.parse( data );
*/
//            case UNK03:
//                return Unk03Text.parse( data );
/*
            case Alarm_Notify:
                return Alarm_Notify.parse( data );
*/
//            case UNK02:
//                return Unk02Text.parse( data );
/*
            case Keep_Alive:
                return Keep_Alive.parse( data );
*/
            default:
                throw new IllegalArgumentException( "Unexpected value=" + this );
        }
    }

}
