package com.sibilantsolutions.grison.net.netty.codec;

import static com.sibilantsolutions.grison.net.netty.codec.parse.NettyByteBufHelper.readBytes;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.Unk02TextDto;
import io.netty.buffer.ByteBuf;

public class Unk02TextDtoParser implements Function<ByteBuf, Unk02TextDto> {
    @Override
    public Unk02TextDto apply(ByteBuf buf) {
        return Unk02TextDto.builder()
                .data(readBytes(Unk02TextDto.DATA_LEN, buf))
                .build();
    }
}
