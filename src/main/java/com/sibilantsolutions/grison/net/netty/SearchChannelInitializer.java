package com.sibilantsolutions.grison.net.netty;

import java.nio.ByteOrder;
import java.time.Clock;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.net.netty.codec.FoscamCommandDtoDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DatagramPacketDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class SearchChannelInitializer extends ChannelInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(SearchChannelInitializer.class);

    private static final int READ_TIMEOUT_SECONDS = 13;

    @Override
    protected void initChannel(Channel ch) {
        LOG.info("{} initChannel.", ch);

        ch.pipeline()
                //Log lifecycle events ("com.sibilantsolutions.grison.net.netty.ChannelLifecycleLoggingHandler"; INFO level).
                .addLast(new ChannelLifecycleLoggingHandler())
                //Log lifecycle events AND datastream ("io.netty.handler.logging.LoggingHandler"; DEBUG level).
                .addLast(new LoggingHandler())
                .addLast(new DatagramPacketDecoder(new MessageToMessageDecoder<ByteBuf>() {
                    @Override
                    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
                        //Extract the ByteBuf from the DatagramPacket and pass it along.
                        //It has to be retained here since it still needs to be consumed downstream.
                        out.add(msg.retain());
                    }
                }))
                //For UDP, the LengthFieldBasedFrameDecoder isn't used to assemble packets across
                //multiple receives, since UDP is unordered.  The packets in this protocol are always
                //small enough to be sent in one indivisible chunk.  Here it's just used to validate
                //the packet format and do a sanity check.
                .addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, 128, 0x0F, 4, 4, 0, true))
                .addLast(new FoscamCommandDtoDecoder(Clock.systemUTC()))
                //Only wait for responses for so long.  Fires ReadTimeoutException and closes the channel.
                .addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS))
                .addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        if (cause instanceof ReadTimeoutException) {
                            LOG.info("{} Read timeout={}secs; channel will be closed.", ctx.channel(), READ_TIMEOUT_SECONDS);
                        } else {
                            super.exceptionCaught(ctx, cause);
                        }
                    }
                })
                .addLast(new SimpleChannelInboundHandler<CommandDto>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, CommandDto msg) {
                        LOG.info("Got msg={}.", msg);
                        //TODO: Fire to subscriber.
                    }
                })
                .addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                        LOG.error("{} exception at end of pipeline; closing channel:", ctx.channel(),
                                new RuntimeException(cause));
                        ctx.close();
                    }
                })
        ;

        ch.closeFuture().addListener((ChannelFutureListener) future -> {
            final EventLoopGroup group = future.channel().eventLoop().parent();
            LOG.info("{} Channel closed, shutting down group={}.", future.channel(), group);
            group.shutdownGracefully(0, 2, TimeUnit.SECONDS);
        });
    }
}
