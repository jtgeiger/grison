package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.AudioEndTextDto;
import io.netty.buffer.ByteBuf;

public class AudioEndTextDtoParser implements Function<ByteBuf, AudioEndTextDto> {
    @Override
    public AudioEndTextDto apply(ByteBuf buf) {
        return AudioEndTextDto.builder().build();
    }
}
