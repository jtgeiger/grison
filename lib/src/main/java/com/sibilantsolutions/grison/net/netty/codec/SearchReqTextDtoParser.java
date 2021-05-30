package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.SearchReqTextDto;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public class SearchReqTextDtoParser implements Function<ByteBuf, SearchReqTextDto> {
    @Override
    public SearchReqTextDto apply(ByteBuf buf) {
        return SearchReqTextDto.builder()
                .reserve1(NettyFosTypeReader.fosInt8(buf))
                .reserve2(NettyFosTypeReader.fosInt8(buf))
                .reserve3(NettyFosTypeReader.fosInt8(buf))
                .reserve4(NettyFosTypeReader.fosInt8(buf))
                .build();
    }
}
