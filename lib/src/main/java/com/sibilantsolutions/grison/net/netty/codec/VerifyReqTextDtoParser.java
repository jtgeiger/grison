package com.sibilantsolutions.grison.net.netty.codec;

import static com.sibilantsolutions.grison.net.netty.codec.parse.NettyByteBufHelper.readBytes;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import io.netty.buffer.ByteBuf;

public class VerifyReqTextDtoParser implements Function<ByteBuf, VerifyReqTextDto> {
    @Override
    public VerifyReqTextDto apply(ByteBuf buf) {
        return parse.apply(buf);
    }

    public static final Function<ByteBuf, VerifyReqTextDto> parse = buf -> VerifyReqTextDto.builder()
            .user(readBytes(VerifyReqTextDto.USER_LEN, buf))
            .password(readBytes(VerifyReqTextDto.PASSWORD_LEN, buf))
            .build();
}
