package com.sibilantsolutions.grison.driver.foscam.dto;

import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;

public enum FoscamOpCode {

    Login_Req_Operation(ProtocolE.OPERATION_PROTOCOL, OperationValues.Login_Req),
    Login_Resp(ProtocolE.OPERATION_PROTOCOL, OperationValues.Login_Resp),
    Verify_Req(ProtocolE.OPERATION_PROTOCOL, OperationValues.Verify_Req),
    Verify_Resp(ProtocolE.OPERATION_PROTOCOL, OperationValues.Verify_Resp),
    Video_Start_Req(ProtocolE.OPERATION_PROTOCOL, OperationValues.Video_Start_Req),
    Video_Start_Resp(ProtocolE.OPERATION_PROTOCOL, OperationValues.Video_Start_Resp),
    Video_End(ProtocolE.OPERATION_PROTOCOL, OperationValues.Video_End),
    Audio_Start_Req(ProtocolE.OPERATION_PROTOCOL, OperationValues.Audio_Start_Req),
    Audio_Start_Resp(ProtocolE.OPERATION_PROTOCOL, OperationValues.Audio_Start_Resp),
    Audio_End(ProtocolE.OPERATION_PROTOCOL, OperationValues.Audio_End),
    Talk_Start_Req(ProtocolE.OPERATION_PROTOCOL, OperationValues.Talk_Start_Req),
    Talk_Start_Resp(ProtocolE.OPERATION_PROTOCOL, OperationValues.Talk_Start_Resp),
    Talk_End(ProtocolE.OPERATION_PROTOCOL, OperationValues.Talk_End),
    UNK01(ProtocolE.OPERATION_PROTOCOL, OperationValues.UNK01),
    UNK03(ProtocolE.OPERATION_PROTOCOL, OperationValues.UNK03),
    Alarm_Notify(ProtocolE.OPERATION_PROTOCOL, OperationValues.Alarm_Notify),
    UNK02(ProtocolE.OPERATION_PROTOCOL, OperationValues.UNK02),
    Keep_Alive_Operation(ProtocolE.OPERATION_PROTOCOL, OperationValues.Keep_Alive),

    Login_Req_AudioVideo(ProtocolE.AUDIO_VIDEO_PROTOCOL, AudioVideoValues.Login_Req),
    Video_Data(ProtocolE.AUDIO_VIDEO_PROTOCOL, AudioVideoValues.Video_Data),
    Audio_Data(ProtocolE.AUDIO_VIDEO_PROTOCOL, AudioVideoValues.Audio_Data),
    Talk_Data(ProtocolE.AUDIO_VIDEO_PROTOCOL, AudioVideoValues.Talk_Data),
    Keep_Alive_AudioVideo(ProtocolE.AUDIO_VIDEO_PROTOCOL, AudioVideoValues.Keep_Alive),

    Search_Req(ProtocolE.SEARCH_PROTOCOL, SearchValues.Search_Req),
    Search_Resp(ProtocolE.SEARCH_PROTOCOL, SearchValues.Search_Resp),
    Init_Req(ProtocolE.SEARCH_PROTOCOL, SearchValues.Init_Req),
    Init_Resp(ProtocolE.SEARCH_PROTOCOL, SearchValues.Init_Resp),
    ;

    private interface OperationValues {
        short Login_Req = 0;    //user -> IP camera
        short Login_Resp = 1;    //IP camera -> user
        short Verify_Req = 2;    //user -> IP camera
        short Verify_Resp = 3;    //ipcamera -> user
        short Video_Start_Req = 4;    //user -> ipcamera
        short Video_Start_Resp = 5;    //ipcamera -> user
        short Video_End = 6;    //user -> ipcamera
        short Audio_Start_Req = 8;    //user -> ipcamera
        short Audio_Start_Resp = 9;    //ipcamera -> user
        short Audio_End = 10;   //0x0A  //user -> ipcamera
        short Talk_Start_Req = 11;   //0x0B  //user -> ipcamera
        short Talk_Start_Resp = 12;   //0x0C  //ipcamera -> user
        short Talk_End = 13;   //0x0D  //user -> ipcamera
        short UNK01 = 16;   //0x10  //user -> ipcamera
        short UNK03 = 17;   //0x11  //ipcamera -> user
        short Alarm_Notify = 25;   //0x19  //ipcamera -> user
        short UNK02 = 28;   //0x1C  //ipcamera -> user
        short Keep_Alive = 255;  //0xFF  //ipcamera <-> user
    }

    private interface AudioVideoValues {
        short Login_Req = 0;    //user -> ipcamera
        short Video_Data = 1;    //ipcamera -> user
        short Audio_Data = 2;    //ipcamera -> user
        short Talk_Data = 3;    //user -> ipcamera
        short Keep_Alive = 255;  //user <-> ipcamera
    }

    private interface SearchValues {
        short Search_Req = 0;    //user -> broadcast address
        short Search_Resp = 1;    //camera -> broadcast address
        short Init_Req = 2;    //user -> broadcast address
        short Init_Resp = 3;    //camera -> broadcast address
    }

    public final ProtocolE protocol;
    public final FosInt16 value;

    FoscamOpCode(ProtocolE protocol, short value) {
        this.protocol = protocol;
        this.value = FosInt16.create(value);
    }

    public static FoscamOpCode fromValue(ProtocolE protocol, FosInt16 value) {
        for (FoscamOpCode foscamOpCode : values()) {
            if (foscamOpCode.protocol == protocol && foscamOpCode.value.equals(value)) {
                return foscamOpCode;
            }
        }

        throw new IllegalArgumentException(String.format("Unexpected protocol=%s, value=%s", protocol, value));
    }

}
