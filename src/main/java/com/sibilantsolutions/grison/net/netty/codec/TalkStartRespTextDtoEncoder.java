package com.sibilantsolutions.grison.net.netty.codec;

import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.dto.TalkStartRespTextDto;
import com.sibilantsolutions.grison.net.netty.codec.dto.FoscamTextByteBufDTO;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeWriter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class TalkStartRespTextDtoEncoder extends MessageToMessageEncoder<TalkStartRespTextDto> {
    @Override
    protected void encode(ChannelHandlerContext ctx, TalkStartRespTextDto msg, List<Object> out) {

        final ByteBuf textBuf = Unpooled.buffer(msg.encodedLength(), msg.encodedLength());

        NettyFosTypeWriter.write(msg.result(), textBuf);
        msg.dataConnectionId().ifPresent(dataConnectionId -> NettyFosTypeWriter.write(dataConnectionId, textBuf));

        out.add(FoscamTextByteBufDTO.create(msg.opCode(), textBuf));

    }

}
