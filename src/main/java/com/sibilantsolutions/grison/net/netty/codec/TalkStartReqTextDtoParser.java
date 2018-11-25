package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.TalkStartReqTextDto;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public class TalkStartReqTextDtoParser implements Function<ByteBuf, TalkStartReqTextDto> {
    @Override
    public TalkStartReqTextDto apply(ByteBuf buf) {
        return TalkStartReqTextDto.builder().reserve(NettyFosTypeReader.fosInt8(buf)).build();
    }
}
