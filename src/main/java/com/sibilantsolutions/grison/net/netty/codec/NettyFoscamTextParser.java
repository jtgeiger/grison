package com.sibilantsolutions.grison.net.netty.codec;

import static com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto.FIRMWARE_VERSION_LEN;
import static com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto.RESERVE1;
import static com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto.RESERVE2;
import static com.sibilantsolutions.grison.net.netty.codec.parse.NettyByteBufHelper.readBytes;

import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.AlarmNotifyTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioEndTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamOpCode;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.InitReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.InitRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveAudioVideoTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqAudioVideoTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.SearchReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.SearchRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkEndTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.Unk02TextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoEndTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public final class NettyFoscamTextParser {

    private NettyFoscamTextParser() {
    }

    public static FoscamTextDto parse(FoscamOpCode foscamOpCode, ByteBuf buf) {
        switch (foscamOpCode) {
            case Login_Req_Operation:
                return loginReqOperation(buf);

            case Login_Resp:
                return loginRespDto(buf);

            case Verify_Req:
                return verifyReq(buf);

            case Verify_Resp:
                return verifyResp(buf);

            case UNK02:
                return unk02(buf);

            case Keep_Alive_Operation:
                return keepAliveOperation(buf);

            case Keep_Alive_AudioVideo:
                return keepAliveAudioVideo(buf);

            case Video_Start_Req:
                return videoStartReq(buf);

            case Video_Start_Resp:
                return videoStartResp(buf);

            case Video_End:
                return videoEnd(buf);

            case Audio_Start_Req:
                return audioStartReq(buf);

            case Audio_Start_Resp:
                return audioStartResp(buf);

            case Audio_End:
                return audioEnd(buf);

            case Talk_Start_Req:
                return talkStartReq(buf);

            case Talk_Start_Resp:
                return talkStartResp(buf);

            case Talk_End:
                return talkEnd(buf);

            case Alarm_Notify:
                return alarmNotify(buf);

            case Login_Req_AudioVideo:
                return loginReqAudioVideo(buf);

            case Video_Data:
                return videoData(buf);

            case Audio_Data:
                return audioData(buf);

            case Talk_Data:
                return talkData(buf);

            case Search_Req:
                return searchReq(buf);

            case Search_Resp:
                return searchResp(buf);

            case Init_Req:
                return initReq(buf);

            case Init_Resp:
                return initResp(buf);

            default:
                throw new IllegalArgumentException(String.format("Unexpected foscamOpCode=%s", foscamOpCode));
        }
    }

    private static TalkDataTextDto talkData(ByteBuf buf) {
        final FosInt32 timestampMs = NettyFosTypeReader.fosInt32(buf);
        final FosInt32 snOfPacket = NettyFosTypeReader.fosInt32(buf);
        final FosInt32 gatherTimeSecs = NettyFosTypeReader.fosInt32(buf);
        final FosInt8 audioFormat = NettyFosTypeReader.fosInt8(buf);
        final FosInt32 dataLength = NettyFosTypeReader.fosInt32(buf);
        final byte[] data = readBytes(dataLength.value(), buf);

        return TalkDataTextDto.builder()
                .timestampMs(timestampMs)
                .snOfPacket(snOfPacket)
                .gatherTimeSecs(gatherTimeSecs)
                .audioFormat(audioFormat)
                .dataLength(dataLength)
                .data(data)
                .build();
    }

    private static AudioDataTextDto audioData(ByteBuf buf) {
        final FosInt32 timestampMs = NettyFosTypeReader.fosInt32(buf);
        final FosInt32 snOfPacket = NettyFosTypeReader.fosInt32(buf);
        final FosInt32 gatherTimeSecs = NettyFosTypeReader.fosInt32(buf);
        final FosInt8 audioFormat = NettyFosTypeReader.fosInt8(buf);
        final FosInt32 dataLength = NettyFosTypeReader.fosInt32(buf);
        final byte[] data = readBytes(dataLength.value(), buf);

        return AudioDataTextDto.builder()
                .timestampMs(timestampMs)
                .snOfPacket(snOfPacket)
                .gatherTimeSecs(gatherTimeSecs)
                .audioFormat(audioFormat)
                .dataLength(dataLength)
                .data(data)
                .build();
    }

    private static AlarmNotifyTextDto alarmNotify(ByteBuf buf) {
        return AlarmNotifyTextDto.builder()
                .type(NettyFosTypeReader.fosInt8(buf))
                .reserve1(NettyFosTypeReader.fosInt16(buf))
                .reserve2(NettyFosTypeReader.fosInt16(buf))
                .reserve3(NettyFosTypeReader.fosInt16(buf))
                .reserve4(NettyFosTypeReader.fosInt16(buf))
                .build();
    }

    private static InitRespTextDto initResp(ByteBuf buf) {
        return InitRespTextDto.builder()
                .resultCode(NettyFosTypeReader.fosInt16(buf))
                .build();
    }

    private static InitReqTextDto initReq(ByteBuf buf) {
        return InitReqTextDto.builder()
                .reserve1(NettyFosTypeReader.fosInt8(buf))
                .reserve2(NettyFosTypeReader.fosInt8(buf))
                .reserve3(NettyFosTypeReader.fosInt8(buf))
                .reserve4(NettyFosTypeReader.fosInt8(buf))
                .cameraId(readBytes(InitReqTextDto.CAMERA_ID_LEN, buf))
                .user(readBytes(InitReqTextDto.USER_LEN, buf))
                .password(readBytes(InitReqTextDto.PASSWORD_LEN, buf))
                .ip(NettyFosTypeReader.fosInt32R(buf))
                .mask(NettyFosTypeReader.fosInt32R(buf))
                .gateway(NettyFosTypeReader.fosInt32R(buf))
                .dns(NettyFosTypeReader.fosInt32R(buf))
                .cameraPort(NettyFosTypeReader.fosInt16R(buf))
                .build();
    }

    private static SearchRespTextDto searchResp(ByteBuf buf) {
        return SearchRespTextDto.builder()
                .cameraId(readBytes(SearchRespTextDto.CAMERA_ID_LEN, buf))
                .cameraName(readBytes(SearchRespTextDto.CAMERA_NAME_LEN, buf))
                .ip(NettyFosTypeReader.fosInt32R(buf))
                .mask(NettyFosTypeReader.fosInt32R(buf))
                .gateway(NettyFosTypeReader.fosInt32R(buf))
                .dns(NettyFosTypeReader.fosInt32R(buf))
                .reserve(readBytes(SearchRespTextDto.RESERVE.length, buf))
                .sysSoftwareVersion(readBytes(SearchRespTextDto.SYS_SOFTWARE_VERSION_LEN, buf))
                .appSoftwareVersion(readBytes(SearchRespTextDto.APP_SOFTWARE_VERSION_LEN, buf))
                .cameraPort(NettyFosTypeReader.fosInt16R(buf))
                .dhcpEnabled(NettyFosTypeReader.fosInt8(buf))
                .build();
    }

    private static SearchReqTextDto searchReq(ByteBuf buf) {
        return SearchReqTextDto.builder()
                .reserve1(NettyFosTypeReader.fosInt8(buf))
                .reserve2(NettyFosTypeReader.fosInt8(buf))
                .reserve3(NettyFosTypeReader.fosInt8(buf))
                .reserve4(NettyFosTypeReader.fosInt8(buf))
                .build();
    }

    private static VideoDataTextDto videoData(ByteBuf buf) {
        final FosInt32 timestamp = NettyFosTypeReader.fosInt32(buf);
        final FosInt32 framePerSec = NettyFosTypeReader.fosInt32(buf);
        final FosInt8 reserve = NettyFosTypeReader.fosInt8(buf);
        final FosInt32 videoLength = NettyFosTypeReader.fosInt32(buf);
        byte[] videoData = new byte[videoLength.value()];
        buf.readBytes(videoData);

        return VideoDataTextDto.builder()
                .timestamp(timestamp)
                .framePerSec(framePerSec)
                .reserve(reserve)
                .videoLength(videoLength)
                .videoData(videoData)
                .build();
    }

    private static LoginReqAudioVideoTextDto loginReqAudioVideo(ByteBuf buf) {
        return LoginReqAudioVideoTextDto.builder().dataConnectionId(NettyFosTypeReader.fosInt32(buf)).build();
    }

    private static VideoEndTextDto videoEnd(ByteBuf buf) {
        return VideoEndTextDto.builder().build();
    }

    private static VideoStartRespTextDto videoStartResp(ByteBuf buf) {
        final FosInt16 result = NettyFosTypeReader.fosInt16(buf);
        final VideoStartRespTextDto.Builder builder = VideoStartRespTextDto.builder()
                .result(result);
        if (ResultCodeE.fromValue(result) == ResultCodeE.CORRECT) {
            builder.dataConnectionId(NettyFosTypeReader.fosInt32(buf));
        }
        return builder.build();
    }

    private static VideoStartReqTextDto videoStartReq(ByteBuf buf) {
        return VideoStartReqTextDto.builder().reserve(NettyFosTypeReader.fosInt8(buf)).build();
    }

    private static AudioEndTextDto audioEnd(ByteBuf buf) {
        return AudioEndTextDto.builder().build();
    }

    private static AudioStartRespTextDto audioStartResp(ByteBuf buf) {
        final FosInt16 result = NettyFosTypeReader.fosInt16(buf);
        final AudioStartRespTextDto.Builder builder = AudioStartRespTextDto.builder()
                .result(result);
        if (ResultCodeE.fromValue(result) == ResultCodeE.CORRECT) {
            builder.dataConnectionId(NettyFosTypeReader.fosInt32(buf));
        }
        return builder.build();
    }

    private static AudioStartReqTextDto audioStartReq(ByteBuf buf) {
        return AudioStartReqTextDto.builder().reserve(NettyFosTypeReader.fosInt8(buf)).build();
    }

    private static TalkStartReqTextDto talkStartReq(ByteBuf buf) {
        return TalkStartReqTextDto.builder().reserve(NettyFosTypeReader.fosInt8(buf)).build();
    }

    private static TalkEndTextDto talkEnd(ByteBuf buf) {
        return TalkEndTextDto.builder().build();
    }

    private static TalkStartRespTextDto talkStartResp(ByteBuf buf) {
        final FosInt16 result = NettyFosTypeReader.fosInt16(buf);
        final TalkStartRespTextDto.Builder builder = TalkStartRespTextDto.builder()
                .result(result);
        if (ResultCodeE.fromValue(result) == ResultCodeE.CORRECT) {
            builder.dataConnectionId(NettyFosTypeReader.fosInt32(buf));
        }
        return builder.build();
    }

    private static Unk02TextDto unk02(ByteBuf buf) {
        return Unk02TextDto.builder()
                .data(readBytes(Unk02TextDto.DATA_LEN, buf))
                .build();
    }

    private static VerifyRespTextDto verifyResp(ByteBuf buf) {
        final FosInt16 resultCode = NettyFosTypeReader.fosInt16(buf);
        final VerifyRespTextDto.Builder builder = VerifyRespTextDto.builder()
                .resultCode(resultCode);

        if (ResultCodeE.fromValue(resultCode) == ResultCodeE.CORRECT) {
            builder.reserve(NettyFosTypeReader.fosInt8(buf));
        }

        return builder.build();
    }

    private static VerifyReqTextDto verifyReq(ByteBuf buf) {
        return VerifyReqTextDto.builder()
                .user(readBytes(VerifyReqTextDto.USER_LEN, buf))
                .password(readBytes(VerifyReqTextDto.PASSWORD_LEN, buf))
                .build();
    }

    private static LoginReqOperationTextDto loginReqOperation(ByteBuf buf) {
        return LoginReqOperationTextDto.builder().build();
    }

    private static KeepAliveOperationTextDto keepAliveOperation(ByteBuf buf) {
        return KeepAliveOperationTextDto.builder().build();
    }

    private static KeepAliveAudioVideoTextDto keepAliveAudioVideo(ByteBuf buf) {
        return KeepAliveAudioVideoTextDto.builder().build();
    }

    public static LoginRespTextDto loginRespDto(ByteBuf buf) {
        final FosInt16 result = NettyFosTypeReader.fosInt16(buf);

        final LoginRespTextDto.Builder builder = LoginRespTextDto.builder()
                .resultCode(result);

        if (ResultCodeE.fromValue(result) == ResultCodeE.CORRECT) {
            final byte[] cameraId = readBytes(LoginRespDetailsDto.CAMERA_ID_LEN, buf);
            final byte[] reserve1 = readBytes(RESERVE1.length, buf);
            final byte[] reserve2 = readBytes(RESERVE2.length, buf);
            final byte[] firmwareVersion = readBytes(FIRMWARE_VERSION_LEN, buf);

            return builder
                    .loginRespDetails(LoginRespDetailsDto.builder()
                            .cameraId(cameraId)
                            .reserve1(reserve1)
                            .reserve2(reserve2)
                            .firmwareVersion(firmwareVersion)
                            .build())
                    .build();
        } else {
            return builder.build();
        }
    }
}
