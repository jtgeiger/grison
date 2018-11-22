package com.sibilantsolutions.grison.net.netty.codec;

import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.dto.SearchRespTextDto;
import com.sibilantsolutions.grison.net.netty.codec.dto.FoscamTextByteBufDTO;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeWriter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class SearchRespTextDtoEncoder extends MessageToMessageEncoder<SearchRespTextDto> {
    @Override
    protected void encode(ChannelHandlerContext ctx, SearchRespTextDto msg, List<Object> out) {

        final ByteBuf textBuf = Unpooled.buffer(msg.encodedLength(), msg.encodedLength());

        textBuf.writeBytes(msg.cameraId());
        textBuf.writeBytes(msg.cameraName());
        NettyFosTypeWriter.write(msg.ip(), textBuf);
        NettyFosTypeWriter.write(msg.mask(), textBuf);
        NettyFosTypeWriter.write(msg.gateway(), textBuf);
        NettyFosTypeWriter.write(msg.dns(), textBuf);
        textBuf.writeBytes(msg.reserve());
        textBuf.writeBytes(msg.sysSoftwareVersion());
        textBuf.writeBytes(msg.appSoftwareVersion());
        NettyFosTypeWriter.write(msg.cameraPort(), textBuf);
        NettyFosTypeWriter.write(msg.dhcpEnabled(), textBuf);

        out.add(FoscamTextByteBufDTO.create(msg.opCode(), textBuf));

    }

}
