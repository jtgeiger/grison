package com.sibilantsolutions.grison.net.netty.codec;

import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.dto.VideoDataTextDto;
import com.sibilantsolutions.grison.net.netty.codec.dto.FoscamTextByteBufDTO;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeWriter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class VideoDataTextDtoEncoder extends MessageToMessageEncoder<VideoDataTextDto> {
    @Override
    protected void encode(ChannelHandlerContext ctx, VideoDataTextDto msg, List<Object> out) {

        final ByteBuf textBuf = ctx.alloc().buffer(msg.encodedLength(), msg.encodedLength());

        NettyFosTypeWriter.write(msg.timestampHundredths(), textBuf);
        NettyFosTypeWriter.write(msg.framePerSec(), textBuf);
        NettyFosTypeWriter.write(msg.reserve(), textBuf);
        NettyFosTypeWriter.write(msg.videoLength(), textBuf);
        textBuf.writeBytes(msg.videoData());

        out.add(FoscamTextByteBufDTO.create(msg.opCode(), textBuf));

    }

}
