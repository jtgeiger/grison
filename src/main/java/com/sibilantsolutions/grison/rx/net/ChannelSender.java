package com.sibilantsolutions.grison.rx.net;

import java.util.concurrent.CancellationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.entity.FoscamTextEntity;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.internal.disposables.CancellableDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChannelSender {

    private static final Logger LOG = LoggerFactory.getLogger(ChannelSender.class);

    private final Channel channel;

    public ChannelSender(Channel channel) {
        this.channel = channel;
    }

    public Flowable<ChannelSendEvent> doSend(FoscamTextEntity entity) {
        return Flowable.create(emitter -> {

            final Scheduler.Worker worker = Schedulers.io().createWorker();

            final CompositeDisposable compositeDisposable = new CompositeDisposable(worker);

            emitter.setDisposable(compositeDisposable);

            worker.schedule(() -> emitter.onNext(ChannelSendEvent.IN_FLIGHT));

            final ChannelFuture channelFuture = channel
                    .writeAndFlush(entity)
                    .addListener((ChannelFuture future) -> {
                        if (future.isSuccess()) {
                            worker.schedule(() -> emitter.onNext(ChannelSendEvent.SENT));
                        } else if (future.cause() != null) {
                            LOG.error("Can't send: future={}:", future, future.cause());
                            worker.schedule(() -> emitter.onNext(ChannelSendEvent.fail(new RuntimeException(future.cause()))));
                        } else if (future.isCancelled()) {
                            worker.schedule(() -> emitter.onNext(ChannelSendEvent.fail(new CancellationException("Future was cancelled"))));
                        } else {
                            worker.schedule(() -> emitter.onNext(ChannelSendEvent.fail(new IllegalArgumentException("Channel future is in a weird state"))));
                        }

                        worker.schedule(worker::dispose);
                    });

            final CancellableDisposable cancellableDisposable = new CancellableDisposable(() -> channelFuture.cancel(true));

            compositeDisposable.add(cancellableDisposable);
        }, BackpressureStrategy.BUFFER);
    }

}
