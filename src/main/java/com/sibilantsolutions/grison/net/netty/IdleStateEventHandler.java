package com.sibilantsolutions.grison.net.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class IdleStateEventHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            throw new RuntimeException("IdleStateEvent: " + idleStateEvent.state() + ", " + idleStateEvent.isFirst());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
