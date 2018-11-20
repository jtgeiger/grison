package com.sibilantsolutions.grison.net.netty.codec;

import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import com.sibilantsolutions.grison.net.netty.codec.dto.FoscamTextByteBufDTO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class VerifyReqTextDtoEncoder extends MessageToMessageEncoder<VerifyReqTextDto> {
    @Override
    protected void encode(ChannelHandlerContext ctx, VerifyReqTextDto msg, List<Object> out) {

        final ByteBuf textBuf = Unpooled.buffer(msg.encodedLength(), msg.encodedLength());

        textBuf.writeBytes(msg.user());
        textBuf.writeBytes(msg.password());

        out.add(FoscamTextByteBufDTO.create(msg.opCode(), textBuf));

    }

}
