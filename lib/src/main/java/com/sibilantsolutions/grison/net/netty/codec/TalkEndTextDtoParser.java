package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.TalkEndTextDto;
import io.netty.buffer.ByteBuf;

public class TalkEndTextDtoParser implements Function<ByteBuf, TalkEndTextDto> {
    @Override
    public TalkEndTextDto apply(ByteBuf buf) {
        return TalkEndTextDto.builder().build();
    }
}
