package com.sibilantsolutions.grison.net.netty.codec;

import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.dto.InitReqTextDto;
import com.sibilantsolutions.grison.net.netty.codec.dto.FoscamTextByteBufDTO;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeWriter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class InitReqTextDtoEncoder extends MessageToMessageEncoder<InitReqTextDto> {
    @Override
    protected void encode(ChannelHandlerContext ctx, InitReqTextDto msg, List<Object> out) {

        final ByteBuf textBuf = Unpooled.buffer(msg.encodedLength(), msg.encodedLength());

        NettyFosTypeWriter.write(msg.reserve1(), textBuf);
        NettyFosTypeWriter.write(msg.reserve2(), textBuf);
        NettyFosTypeWriter.write(msg.reserve3(), textBuf);
        NettyFosTypeWriter.write(msg.reserve4(), textBuf);
        textBuf.writeBytes(msg.cameraId());
        textBuf.writeBytes(msg.user());
        textBuf.writeBytes(msg.password());
        NettyFosTypeWriter.write(msg.ip(), textBuf);
        NettyFosTypeWriter.write(msg.mask(), textBuf);
        NettyFosTypeWriter.write(msg.gateway(), textBuf);
        NettyFosTypeWriter.write(msg.dns(), textBuf);
        NettyFosTypeWriter.write(msg.cameraPort(), textBuf);

        out.add(FoscamTextByteBufDTO.create(msg.opCode(), textBuf));

    }

}
