package com.sibilantsolutions.grison.net.netty.codec;

import static com.sibilantsolutions.grison.net.netty.codec.parse.NettyByteBufHelper.readBytes;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.InitReqTextDto;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public class InitReqTextDtoParser implements Function<ByteBuf, InitReqTextDto> {
    @Override
    public InitReqTextDto apply(ByteBuf buf) {
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
}
