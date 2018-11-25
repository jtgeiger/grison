package com.sibilantsolutions.grison.net.netty.codec;

import com.sibilantsolutions.grison.driver.foscam.dto.FoscamOpCode;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamTextDto;
import io.netty.buffer.ByteBuf;

public final class NettyFoscamTextParser {

    private NettyFoscamTextParser() {
    }

    public static FoscamTextDto parse(FoscamOpCode foscamOpCode, ByteBuf buf) {
        switch (foscamOpCode) {
            case Login_Req_Operation:
                return new LoginReqOperationTextDtoParser().apply(buf);

            case Login_Resp:
                return new LoginRespTextDtoParser().apply(buf);

            case Verify_Req:
                return new VerifyReqTextDtoParser().apply(buf);

            case Verify_Resp:
                return new VerifyRespTextDtoParser().apply(buf);

            case UNK02:
                return new Unk02TextDtoParser().apply(buf);

            case Keep_Alive_Operation:
                return new KeepAliveOperationTextDtoParser().apply(buf);

            case Keep_Alive_AudioVideo:
                return new KeepAliveAudioVideoTextDtoParser().apply(buf);

            case Video_Start_Req:
                return new VideoStartReqTextDtoParser().apply(buf);

            case Video_Start_Resp:
                return new VideoStartRespTextDtoParser().apply(buf);

            case Video_End:
                return new VideoEndTextDtoParser().apply(buf);

            case Audio_Start_Req:
                return new AudioStartReqTextDtoParser().apply(buf);

            case Audio_Start_Resp:
                return new AudioStartRespTextDtoParser().apply(buf);

            case Audio_End:
                return new AudioEndTextDtoParser().apply(buf);

            case Talk_Start_Req:
                return new TalkStartReqTextDtoParser().apply(buf);

            case Talk_Start_Resp:
                return new TalkStartRespTextDtoParser().apply(buf);

            case Talk_End:
                return new TalkEndTextDtoParser().apply(buf);

            case Alarm_Notify:
                return new AlarmNotifyTextDtoParser().apply(buf);

            case Login_Req_AudioVideo:
                return new LoginReqAudioVideoTextDtoParser().apply(buf);

            case Video_Data:
                return new VideoDataTextDtoParser().apply(buf);

            case Audio_Data:
                return new AudioDataTextDtoParser().apply(buf);

            case Talk_Data:
                return new TalkDataTextDtoParser().apply(buf);

            case Search_Req:
                return new SearchReqTextDtoParser().apply(buf);

            case Search_Resp:
                return new SearchRespTextDtoParser().apply(buf);

            case Init_Req:
                return new InitReqTextDtoParser().apply(buf);

            case Init_Resp:
                return new InitRespTextDtoParser().apply(buf);

            default:
                throw new IllegalArgumentException(String.format("Unexpected foscamOpCode=%s", foscamOpCode));
        }
    }

}
