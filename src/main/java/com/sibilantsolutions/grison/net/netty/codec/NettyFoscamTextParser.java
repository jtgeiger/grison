package com.sibilantsolutions.grison.net.netty.codec;

import static com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto.CAMERA_ID_LEN;
import static com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto.FIRMWARE_VERSION_LEN;
import static com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto.RESERVE1_LEN;
import static com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto.RESERVE2_LEN;
import static com.sibilantsolutions.grison.net.netty.codec.parse.NettyByteBufHelper.readBytes;

import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamOpCode;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public final class NettyFoscamTextParser {

    private NettyFoscamTextParser() {
    }

    public static FoscamTextDto parse(FoscamOpCode foscamOpCode, ByteBuf buf) {
        switch (foscamOpCode) {
            case Login_Resp:
                return loginRespDto(buf);

            default:
                throw new IllegalArgumentException(String.format("Unexpected foscamOpCode=%s", foscamOpCode));
        }
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
