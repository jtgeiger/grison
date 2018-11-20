package com.sibilantsolutions.grison.net.netty.codec;

import static com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto.CAMERA_ID_LEN;
import static com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto.FIRMWARE_VERSION_LEN;
import static com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto.RESERVE1_LEN;
import static com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto.RESERVE2_LEN;
import static com.sibilantsolutions.grison.net.netty.codec.parse.NettyByteBufHelper.readBytes;

import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamOpCode;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveAudioVideoTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.Unk02TextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;
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

            default:
                throw new IllegalArgumentException(String.format("Unexpected foscamOpCode=%s", foscamOpCode));
        }
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

        if (resultCode.value() == ResultCodeE.CORRECT.getValue()) {
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

        if (ResultCodeE.fromValue(result.value()) == ResultCodeE.CORRECT) {
            final byte[] cameraId = readBytes(CAMERA_ID_LEN, buf);
            final byte[] reserve1 = readBytes(RESERVE1_LEN, buf);
            final byte[] reserve2 = readBytes(RESERVE2_LEN, buf);
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
