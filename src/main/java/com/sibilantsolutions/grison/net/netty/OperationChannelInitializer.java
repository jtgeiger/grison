package com.sibilantsolutions.grison.net.netty;

import java.nio.ByteOrder;
import java.time.Clock;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamOpCode;
import com.sibilantsolutions.grison.net.netty.codec.FoscamCommandDtoDecoder;
import com.sibilantsolutions.grison.net.netty.codec.FoscamTextByteBufDTOEncoder;
import com.sibilantsolutions.grison.net.netty.codec.FoscamTextDtoEncoder;
import com.sibilantsolutions.grison.net.netty.codec.FoscamTextEntityToFoscamTextDto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class OperationChannelInitializer extends ChannelInitializer<Channel> {

    private static final Logger LOG = LoggerFactory.getLogger(OperationChannelInitializer.class);

    private static final String EXCEPTION_TRAPPER = "exceptionTrapper";

    //Max is UNK02 at 1175 bytes.
    private static final int MAX_FRAME_LENGTH = 1_200;

    private static final Duration READ_TIMEOUT = Duration.ofMinutes(2);
    private static final Duration KEEPALIVE_SEND_TIMEOUT = Duration.ofSeconds(73);
    private static final Duration WRITE_TIMEOUT = KEEPALIVE_SEND_TIMEOUT.plus(Duration.ofSeconds(5));

    private final Subscriber<CommandDto> operationDatastream;

    public OperationChannelInitializer(Subscriber<CommandDto> operationDatastream) {
        this.operationDatastream = operationDatastream;
    }

    @Override
    protected void initChannel(Channel ch) {
        LOG.info("{} initChannel.", ch);

        ch.pipeline()
                //Log lifecycle events ("com.sibilantsolutions.grison.net.netty.ChannelLifecycleLoggingHandler"; INFO level).
                .addLast(new ChannelLifecycleLoggingHandler())
                //Log lifecycle events AND datastream ("io.netty.handler.logging.LoggingHandler"; DEBUG level).
                .addLast(new LoggingHandler())
                .addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, MAX_FRAME_LENGTH, 0x0F, 4, 4, 0, true))
                .addLast(new FoscamCommandDtoDecoder(Clock.systemUTC()))
                .addLast(new FoscamTextByteBufDTOEncoder())
                .addLast(new FoscamTextDtoEncoder())
                .addLast(new FoscamTextEntityToFoscamTextDto())

                .addLast(new IdleStateHandler((int)READ_TIMEOUT.getSeconds(), (int)WRITE_TIMEOUT.getSeconds(), 0))
                .addLast(new IdleStateEventHandler())
                //Receive and drop inbound pings.  We don't respond to these.  We send outbound
                //pings on a set schedule, not dependent on inbound pings.
                .addLast(new KeepAliveInboundDropper(FoscamOpCode.Keep_Alive_Operation))
                //Emit KeepAliveTimerEvents at regular intervals.
                .addLast(new KeepAliveTimerEventScheduler(KEEPALIVE_SEND_TIMEOUT))
                .addLast(new KeepAliveSender(FoscamOpCode.Keep_Alive_Operation))
                .addLast(new SimpleChannelInboundHandler<CommandDto>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, CommandDto msg) {
                        operationDatastream.onNext(msg);
                    }

                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) {
                        operationDatastream.onComplete();
                    }
                })
                .addLast(EXCEPTION_TRAPPER, new ChannelInboundHandlerAdapter() {
                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                        LOG.error("{} exception at end of pipeline; closing channel:", ctx.channel(),
                                new RuntimeException(cause));
                        ctx.close();
                    }
                })
        ;

        ch.closeFuture().addListener((ChannelFuture future) -> {
            final EventLoopGroup group = future.channel().eventLoop().parent();
            LOG.info("{} Channel closed, shutting down group={}.", future.channel(), group);
            group.shutdownGracefully(0, 2, TimeUnit.SECONDS);
        });
    }
}
