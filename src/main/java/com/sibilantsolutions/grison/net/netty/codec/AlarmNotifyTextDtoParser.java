package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.AlarmNotifyTextDto;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public class AlarmNotifyTextDtoParser implements Function<ByteBuf, AlarmNotifyTextDto> {
    @Override
    public AlarmNotifyTextDto apply(ByteBuf buf) {
        return AlarmNotifyTextDto.builder()
                .type(NettyFosTypeReader.fosInt8(buf))
                .reserve1(NettyFosTypeReader.fosInt16(buf))
                .reserve2(NettyFosTypeReader.fosInt16(buf))
                .reserve3(NettyFosTypeReader.fosInt16(buf))
                .reserve4(NettyFosTypeReader.fosInt16(buf))
                .build();
    }
}
