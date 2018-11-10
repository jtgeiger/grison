package com.sibilantsolutions.grison.net.netty;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.driver.foscam.domain.KeepAliveText;
import com.sibilantsolutions.grison.driver.foscam.domain.OpCodeI;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class KeepAliveSender extends ChannelInboundHandlerAdapter {
    private final ProtocolE keepAliveProtocol;
    private final OpCodeI keepAliveOpCode;

    public KeepAliveSender(ProtocolE keepAliveProtocol, OpCodeI keepAliveOpCode) {
        this.keepAliveProtocol = keepAliveProtocol;
        this.keepAliveOpCode = keepAliveOpCode;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof KeepAliveTimerEvent) {
            Command c = new Command();
            c.setProtocol(keepAliveProtocol);
            c.setOpCode(keepAliveOpCode);
            KeepAliveText text = new KeepAliveText();
            c.setCommandText(text);

            ctx.writeAndFlush(c)
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
