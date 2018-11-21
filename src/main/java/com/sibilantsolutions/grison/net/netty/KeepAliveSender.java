package com.sibilantsolutions.grison.net.netty;

import com.sibilantsolutions.grison.driver.foscam.dto.FoscamOpCode;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveAudioVideoTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveOperationTextDto;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class KeepAliveSender extends ChannelInboundHandlerAdapter {

    private final FoscamOpCode keepAliveOpCode;

    public KeepAliveSender(FoscamOpCode keepAliveOpCode) {
        this.keepAliveOpCode = keepAliveOpCode;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof KeepAliveTimerEvent) {

            FoscamTextDto text;

            if (keepAliveOpCode.equals(FoscamOpCode.Keep_Alive_Operation)) {
                text = KeepAliveOperationTextDto.builder().build();
            } else if (keepAliveOpCode.equals(FoscamOpCode.Keep_Alive_AudioVideo)) {
                text = KeepAliveAudioVideoTextDto.builder().build();
            } else {
                throw new IllegalArgumentException("Unexpected opCode=" + keepAliveOpCode);
            }

            ctx.writeAndFlush(text)
                    .addListener((ChannelFutureListener) future -> {
                        if (!future.isSuccess()) {
                            throw new RuntimeException("Failed to send keepAlive:", future.cause());
                        }
                    });
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}
