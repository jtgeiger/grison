package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.VideoDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public class VideoDataTextDtoParser implements Function<ByteBuf, VideoDataTextDto> {
    @Override
    public VideoDataTextDto apply(ByteBuf buf) {
        final FosInt32 timestamp = NettyFosTypeReader.fosInt32(buf);
        final FosInt32 framePerSec = NettyFosTypeReader.fosInt32(buf);
        final FosInt8 reserve = NettyFosTypeReader.fosInt8(buf);
        final FosInt32 videoLength = NettyFosTypeReader.fosInt32(buf);
        byte[] videoData = new byte[videoLength.value()];
        buf.readBytes(videoData);

        return VideoDataTextDto.builder()
                .timestamp(timestamp)
                .framePerSec(framePerSec)
                .reserve(reserve)
                .videoLength(videoLength)
                .videoData(videoData)
                .build();
    }
}
