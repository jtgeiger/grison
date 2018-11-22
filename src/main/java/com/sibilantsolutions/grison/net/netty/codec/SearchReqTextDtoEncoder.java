package com.sibilantsolutions.grison.net.netty.codec;

import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.dto.SearchReqTextDto;
import com.sibilantsolutions.grison.net.netty.codec.dto.FoscamTextByteBufDTO;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeWriter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class SearchReqTextDtoEncoder extends MessageToMessageEncoder<SearchReqTextDto> {
    @Override
    protected void encode(ChannelHandlerContext ctx, SearchReqTextDto msg, List<Object> out) {

        final ByteBuf textBuf = encodeToNewBuf(msg);

        out.add(FoscamTextByteBufDTO.create(msg.opCode(), textBuf));

    }

    public static ByteBuf encodeToNewBuf(SearchReqTextDto msg) {
        final ByteBuf textBuf = Unpooled.buffer(msg.encodedLength(), msg.encodedLength());

        NettyFosTypeWriter.write(msg.reserve1(), textBuf);
        NettyFosTypeWriter.write(msg.reserve2(), textBuf);
        NettyFosTypeWriter.write(msg.reserve3(), textBuf);
        NettyFosTypeWriter.write(msg.reserve4(), textBuf);
        return textBuf;
    }

}
