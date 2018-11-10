package com.sibilantsolutions.grison.net.netty;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class KeepAliveTimerEventScheduler extends ChannelInboundHandlerAdapter {

    private final int keepaliveSendTimeoutSecs;

    public KeepAliveTimerEventScheduler(int keepaliveSendTimeoutSecs) {
        this.keepaliveSendTimeoutSecs = keepaliveSendTimeoutSecs;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.executor().scheduleAtFixedRate(() -> ctx.fireUserEventTriggered(
                new KeepAliveTimerEvent(keepaliveSendTimeoutSecs, TimeUnit.SECONDS)),
                keepaliveSendTimeoutSecs, keepaliveSendTimeoutSecs, TimeUnit.SECONDS);

        ctx.pipeline().remove(this);

        ctx.fireChannelActive();
    }
}
