package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public class VerifyRespTextDtoParser implements Function<ByteBuf, VerifyRespTextDto> {
    @Override
    public VerifyRespTextDto apply(ByteBuf buf) {
        final FosInt16 resultCode = NettyFosTypeReader.fosInt16(buf);
        final VerifyRespTextDto.Builder builder = VerifyRespTextDto.builder()
                .resultCode(resultCode);

        if (ResultCodeE.fromValue(resultCode) == ResultCodeE.CORRECT) {
            builder.reserve(NettyFosTypeReader.fosInt8(buf));
        }

        return builder.build();

    }
}
