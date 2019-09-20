package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.AudioStartReqTextDto;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public class AudioStartReqTextDtoParser implements Function<ByteBuf, AudioStartReqTextDto> {
    @Override
    public AudioStartReqTextDto apply(ByteBuf buf) {
        return AudioStartReqTextDto.builder().data(NettyFosTypeReader.fosInt8(buf)).build();
    }
}
