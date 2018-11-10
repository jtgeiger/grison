package com.sibilantsolutions.grison.net.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.driver.foscam.domain.KeepAliveText;
import com.sibilantsolutions.grison.driver.foscam.domain.OpCodeI;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Drops inbound keep alive messages.
 */
public class KeepAliveInboundDropper extends SimpleChannelInboundHandler<Command> {

    private static final Logger LOG = LoggerFactory.getLogger(KeepAliveInboundDropper.class);

    private final ProtocolE keepAliveProtocol;
    private final OpCodeI keepAliveOpCode;

    public KeepAliveInboundDropper(ProtocolE keepAliveProtocol, OpCodeI keepAliveOpCode) {
        this.keepAliveProtocol = keepAliveProtocol;
        this.keepAliveOpCode = keepAliveOpCode;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command msg) throws Exception {
        if (msg.getProtocol() == keepAliveProtocol
                && msg.getOpCode() == keepAliveOpCode
                && msg.getCommandText() instanceof KeepAliveText) {
            LOG.info("Drop inbound keep alive.");
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
