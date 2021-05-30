package com.sibilantsolutions.grison.net.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ChannelLifecycleLoggingHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ChannelLifecycleLoggingHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LOG.info("{} channelActive.", ctx.channel());
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        LOG.info("{} channelInactive.", ctx.channel());
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        LOG.info("{} channelRegistered.", ctx.channel());
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        LOG.info("{} channelUnregistered.", ctx.channel());
        ctx.fireChannelUnregistered();
    }
}
