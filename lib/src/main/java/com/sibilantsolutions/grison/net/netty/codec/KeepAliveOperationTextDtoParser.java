package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveOperationTextDto;
import io.netty.buffer.ByteBuf;

public class KeepAliveOperationTextDtoParser implements Function<ByteBuf, KeepAliveOperationTextDto> {
    @Override
    public KeepAliveOperationTextDto apply(ByteBuf buf) {
        return KeepAliveOperationTextDto.builder().build();
    }
}
