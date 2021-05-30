package com.sibilantsolutions.grison.net.netty.codec;

import java.time.Clock;
import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

//NOT @ChannelHandler.Sharable
public class FoscamCommandDtoDecoder extends ByteToMessageDecoder {

    private final Clock clock;

    public FoscamCommandDtoDecoder(Clock clock) {
        this.clock = clock;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        final CommandDto commandDto = new NettyCommandDtoParser(clock).parse(in);
        out.add(commandDto);
    }
}
