package com.sibilantsolutions.grison.net.netty.codec;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.net.netty.codec.dto.FoscamTextByteBufDTO;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeWriter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class FoscamTextByteBufDTOEncoder extends MessageToByteEncoder<FoscamTextByteBufDTO> {

    @Override
    public void encode(ChannelHandlerContext ctx, FoscamTextByteBufDTO msg, ByteBuf out) {
        out.writeBytes(msg.opCode().protocol.getValue());   //4
        NettyFosTypeWriter.write(msg.opCode().value, out);  //2
        NettyFosTypeWriter.write(CommandDto.RESERVE1, out); //1
        out.writeBytes(CommandDto.RESERVE2);                //8
        NettyFosTypeWriter.write(msg.encodedLength(), out); //4
        NettyFosTypeWriter.write(msg.encodedLength(), out); //4
        out.writeBytes(msg.textBuf());                      //N

        // Each FoscamTextByteBufDTO has a ByteBuf that was allocated to hold the text; it is our job to release it.
        msg.textBuf().release();
    }

    @Override
    protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, FoscamTextByteBufDTO msg, boolean preferDirect) {
        return ctx.alloc().buffer(CommandDto.COMMAND_PREFIX_LENGTH + msg.encodedLength().value());
    }
}
