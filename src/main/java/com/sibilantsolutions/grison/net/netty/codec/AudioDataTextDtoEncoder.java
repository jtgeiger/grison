package com.sibilantsolutions.grison.net.netty.codec;

import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.dto.AudioDataTextDto;
import com.sibilantsolutions.grison.net.netty.codec.dto.FoscamTextByteBufDTO;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeWriter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class AudioDataTextDtoEncoder extends MessageToMessageEncoder<AudioDataTextDto> {
    @Override
    protected void encode(ChannelHandlerContext ctx, AudioDataTextDto msg, List<Object> out) {

        final ByteBuf textBuf = ctx.alloc().buffer(msg.encodedLength(), msg.encodedLength());

        NettyFosTypeWriter.write(msg.timestampMs(), textBuf);
        NettyFosTypeWriter.write(msg.snOfPacket(), textBuf);
        NettyFosTypeWriter.write(msg.gatherTimeSecs(), textBuf);
        NettyFosTypeWriter.write(msg.audioFormat(), textBuf);
        NettyFosTypeWriter.write(msg.dataLength(), textBuf);
        textBuf.writeBytes(msg.data());

        out.add(FoscamTextByteBufDTO.create(msg.opCode(), textBuf));

    }

}
