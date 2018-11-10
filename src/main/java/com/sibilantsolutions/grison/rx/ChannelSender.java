package com.sibilantsolutions.grison.rx;

import java.util.concurrent.CancellationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.reactivex.Completable;

public class ChannelSender {

    private static final Logger LOG = LoggerFactory.getLogger(ChannelSender.class);

    private final Channel channel;

    public ChannelSender(Channel channel) {
        this.channel = channel;
    }

    public Completable doSend(Command command) {
        return Completable.create(emitter -> {
            final ChannelFuture channelFuture = channel
                    .writeAndFlush(command)
                    .addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            emitter.onComplete();
                        } else if (future.cause() != null) {
                            LOG.error("Can't send: future={}:", future, future.cause());
                            emitter.onError(new RuntimeException(future.cause()));
                        } else if (future.isCancelled()) {
                            emitter.onError(new CancellationException("Future was cancelled"));
                        } else {
                            emitter.onError(new IllegalArgumentException("Channel future is in a weird state"));
                        }
                    });

            emitter.setCancellable(() -> channelFuture.cancel(true));
        });
    }

}
