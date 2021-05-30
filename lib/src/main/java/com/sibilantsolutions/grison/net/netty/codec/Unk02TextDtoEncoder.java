package com.sibilantsolutions.grison.net.netty.codec;

import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.dto.Unk02TextDto;
import com.sibilantsolutions.grison.net.netty.codec.dto.FoscamTextByteBufDTO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class Unk02TextDtoEncoder extends MessageToMessageEncoder<Unk02TextDto> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Unk02TextDto msg, List<Object> out) {

        final ByteBuf textBuf = ctx.alloc().buffer(msg.encodedLength(), msg.encodedLength());

        textBuf.writeBytes(msg.data());

        out.add(FoscamTextByteBufDTO.create(msg.opCode(), textBuf));

    }

}
