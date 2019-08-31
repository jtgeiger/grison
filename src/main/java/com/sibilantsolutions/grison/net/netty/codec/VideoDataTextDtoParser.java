package com.sibilantsolutions.grison.net.netty.codec;

import static com.sibilantsolutions.grison.net.netty.codec.parse.NettyByteBufHelper.readBytes;
import static io.netty.util.internal.ObjectUtil.checkPositiveOrZero;

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
        //Copy the video data out of the buffer into a new array.  Two reasons: we don't want downstream to depend
        //on Netty, and we can't count on downstream to release a buffer.
        byte[] videoData = readBytes(checkPositiveOrZero(videoLength.value().intValue(), "videoLength"), buf);

        return VideoDataTextDto.builder()
                .timestampHundredths(timestamp)
                .framePerSec(framePerSec)
                .reserve(reserve)
                .videoLength(videoLength)
                .videoData(videoData)
                .build();
    }
}
