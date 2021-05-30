package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqOperationTextDto;
import io.netty.buffer.ByteBuf;

public class LoginReqOperationTextDtoParser implements Function<ByteBuf, LoginReqOperationTextDto> {
    @Override
    public LoginReqOperationTextDto apply(ByteBuf buf) {
        return LoginReqOperationTextDto.builder().build();
    }
}
