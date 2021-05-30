package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartReqTextDto;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public class VideoStartReqTextDtoParser implements Function<ByteBuf, VideoStartReqTextDto> {
    @Override
    public VideoStartReqTextDto apply(ByteBuf buf) {
        return VideoStartReqTextDto.builder().reserve(NettyFosTypeReader.fosInt8(buf)).build();
    }
}
