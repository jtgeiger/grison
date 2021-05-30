package com.sibilantsolutions.grison.net.netty.codec;

import static com.sibilantsolutions.grison.net.netty.codec.parse.NettyByteBufHelper.readBytes;
import static io.netty.util.internal.ObjectUtil.checkPositiveOrZero;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.TalkDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public class TalkDataTextDtoParser implements Function<ByteBuf, TalkDataTextDto> {
    @Override
    public TalkDataTextDto apply(ByteBuf buf) {
        final FosInt32 timestampMs = NettyFosTypeReader.fosInt32(buf);
        final FosInt32 snOfPacket = NettyFosTypeReader.fosInt32(buf);
        final FosInt32 gatherTimeSecs = NettyFosTypeReader.fosInt32(buf);
        final FosInt8 audioFormat = NettyFosTypeReader.fosInt8(buf);
        final FosInt32 dataLength = NettyFosTypeReader.fosInt32(buf);
        final byte[] data = readBytes(checkPositiveOrZero(dataLength.value().intValue(), "dataLength"), buf);

        return TalkDataTextDto.builder()
                .timestampMs(timestampMs)
                .snOfPacket(snOfPacket)
                .gatherTimeSecs(gatherTimeSecs)
                .audioFormat(audioFormat)
                .dataLength(dataLength)
                .data(data)
                .build();
    }
}
