package com.sibilantsolutions.grison.net.netty.codec;

import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.dto.InitRespTextDto;
import com.sibilantsolutions.grison.net.netty.codec.dto.FoscamTextByteBufDTO;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeWriter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class InitRespTextDtoEncoder extends MessageToMessageEncoder<InitRespTextDto> {
    @Override
    protected void encode(ChannelHandlerContext ctx, InitRespTextDto msg, List<Object> out) {

        final ByteBuf textBuf = Unpooled.buffer(msg.encodedLength(), msg.encodedLength());

        NettyFosTypeWriter.write(msg.resultCode(), textBuf);

        out.add(FoscamTextByteBufDTO.create(msg.opCode(), textBuf));

    }

}
