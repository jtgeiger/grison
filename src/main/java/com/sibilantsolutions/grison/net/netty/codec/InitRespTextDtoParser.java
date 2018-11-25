package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.InitRespTextDto;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public class InitRespTextDtoParser implements Function<ByteBuf, InitRespTextDto> {
    @Override
    public InitRespTextDto apply(ByteBuf buf) {
        return InitRespTextDto.builder()
                .resultCode(NettyFosTypeReader.fosInt16(buf))
                .build();
    }
}
