package com.sibilantsolutions.grison.net.netty.codec;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqAudioVideoTextDto;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public class LoginReqAudioVideoTextDtoParser implements Function<ByteBuf, LoginReqAudioVideoTextDto> {
    @Override
    public LoginReqAudioVideoTextDto apply(ByteBuf buf) {
        return LoginReqAudioVideoTextDto.builder().dataConnectionId(NettyFosTypeReader.fosInt32(buf)).build();
    }
}
