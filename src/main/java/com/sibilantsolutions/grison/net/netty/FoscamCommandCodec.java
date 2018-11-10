package com.sibilantsolutions.grison.net.netty;

import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

public class FoscamCommandCodec extends ByteToMessageCodec<Command> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Command msg, ByteBuf out) {
        final byte[] bytes = msg.toDatastream();
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        final byte[] bytes;
        final int offset;
        final int length = in.readableBytes();

        //Use the underlying backing array, if available, to avoid making a copy.
        if (in.hasArray()) {
            bytes = in.array();
            offset = in.arrayOffset();
            in.readerIndex(in.readerIndex() + length);
        } else {
            bytes = new byte[length];
            in.readBytes(bytes);
            offset = 0;
        }

        final Command command = Command.parse(bytes, offset, length);

        out.add(command);
    }

}
