package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveAudioVideoTextDto;
import io.netty.buffer.ByteBuf;

public class KeepAliveAudioVideoTextDtoParser implements Function<ByteBuf, KeepAliveAudioVideoTextDto> {
    @Override
    public KeepAliveAudioVideoTextDto apply(ByteBuf buf) {
        return KeepAliveAudioVideoTextDto.builder().build();
    }
}
