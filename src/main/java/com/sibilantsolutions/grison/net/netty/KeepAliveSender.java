package com.sibilantsolutions.grison.net.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.dto.FoscamOpCode;
import com.sibilantsolutions.grison.driver.foscam.entity.FoscamTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.KeepAliveAudioVideoTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.KeepAliveOperationTextEntity;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class KeepAliveSender extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(KeepAliveSender.class);

    private final FoscamOpCode keepAliveOpCode;

    public KeepAliveSender(FoscamOpCode keepAliveOpCode) {
        this.keepAliveOpCode = keepAliveOpCode;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof KeepAliveTimerEvent) {

            FoscamTextEntity entity;

            if (keepAliveOpCode.equals(FoscamOpCode.Keep_Alive_Operation)) {
                entity = KeepAliveOperationTextEntity.builder().build();
            } else if (keepAliveOpCode.equals(FoscamOpCode.Keep_Alive_AudioVideo)) {
                entity = KeepAliveAudioVideoTextEntity.builder().build();
            } else {
                throw new IllegalArgumentException("Unexpected opCode=" + keepAliveOpCode);
            }

            LOG.info("{} send keep alive={}.", ctx.channel(), keepAliveOpCode);

            ctx.writeAndFlush(entity)
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
