package com.sibilantsolutions.grison.net.netty;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class KeepAliveTimerEventScheduler extends ChannelInboundHandlerAdapter {

    private final Duration keepaliveSendTimeout;

    public KeepAliveTimerEventScheduler(Duration keepaliveSendTimeout) {
        this.keepaliveSendTimeout = keepaliveSendTimeout;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.executor().scheduleAtFixedRate(() -> ctx.fireUserEventTriggered(
                new KeepAliveTimerEvent(keepaliveSendTimeout)),
                keepaliveSendTimeout.getSeconds(), keepaliveSendTimeout.getSeconds(), TimeUnit.SECONDS);

        ctx.pipeline().remove(this);

        ctx.fireChannelActive();
    }
}
