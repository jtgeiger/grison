package com.sibilantsolutions.grison.net.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamOpCode;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Drops inbound keep alive messages.
 */
public class KeepAliveInboundDropper extends SimpleChannelInboundHandler<CommandDto> {

    private static final Logger LOG = LoggerFactory.getLogger(KeepAliveInboundDropper.class);

    private final FoscamOpCode keepAliveOpCode;

    public KeepAliveInboundDropper(FoscamOpCode keepAliveOpCode) {
        this.keepAliveOpCode = keepAliveOpCode;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CommandDto msg) {
        if (msg.text().opCode().equals(keepAliveOpCode)) {
            LOG.info("{} Drop inbound keep alive={}.", ctx.channel(), keepAliveOpCode);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
