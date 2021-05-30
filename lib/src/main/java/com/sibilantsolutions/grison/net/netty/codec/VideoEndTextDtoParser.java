package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.VideoEndTextDto;
import io.netty.buffer.ByteBuf;

public class VideoEndTextDtoParser implements Function<ByteBuf, VideoEndTextDto> {
    @Override
    public VideoEndTextDto apply(ByteBuf buf) {
        return VideoEndTextDto.builder().build();
    }
}
