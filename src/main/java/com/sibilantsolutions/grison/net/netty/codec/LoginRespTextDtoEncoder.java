package com.sibilantsolutions.grison.net.netty.codec;

import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.net.netty.codec.dto.FoscamTextByteBufDTO;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeWriter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@ChannelHandler.Sharable
public class LoginRespTextDtoEncoder extends MessageToMessageEncoder<LoginRespTextDto> {
    @Override
    protected void encode(ChannelHandlerContext ctx, LoginRespTextDto msg, List<Object> out) {

        final ByteBuf textBuf = ctx.alloc().buffer(msg.encodedLength(), msg.encodedLength());

        NettyFosTypeWriter.write(msg.resultCode(), textBuf);

        msg.loginRespDetails().ifPresent(loginRespDetailsDto -> {
            textBuf.writeBytes(loginRespDetailsDto.cameraId());
            textBuf.writeBytes(loginRespDetailsDto.reserve1());
            textBuf.writeBytes(loginRespDetailsDto.reserve2());
            textBuf.writeBytes(loginRespDetailsDto.firmwareVersion());
        });

        out.add(FoscamTextByteBufDTO.create(msg.opCode(), textBuf));

    }

}
