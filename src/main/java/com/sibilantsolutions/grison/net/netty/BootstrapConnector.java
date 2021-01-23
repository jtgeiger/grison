package com.sibilantsolutions.grison.net.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.rx.net.ChannelConnectEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.internal.disposables.CancellableDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BootstrapConnector {

    private static final Logger LOG = LoggerFactory.getLogger(BootstrapConnector.class);

    private BootstrapConnector() {
        throw new IllegalStateException("No instances.");
    }

    public static Flowable<ChannelConnectEvent> connect(Bootstrap bootstrap) {
        return Flowable.create(emitter -> {

            //Use a worker to make sure that all emissions occur on the same thread for the sake of consistency for
            //downstream.
            final Scheduler.Worker worker = Schedulers.io().createWorker();

            //Add the worker to a CompositeDisposable that will be set to the emitter.
            //Since the worker is registered to be disposed, we don't need to add any of the schedule() Disposables
            //since those will be cancelled if the worker is disposed.
            final CompositeDisposable compositeDisposable = new CompositeDisposable(worker);

            emitter.setDisposable(compositeDisposable);

            worker.schedule(() -> emitter.onNext(ChannelConnectEvent.IN_FLIGHT));

            LOG.info("Calling connect on bootstrap={}.", bootstrap);

            final ChannelFuture channelFuture = bootstrap
                    .connect()
                    .addListener((ChannelFuture future) -> {

                        if (future.isSuccess()) {
                            worker.schedule(() -> emitter.onNext(ChannelConnectEvent.success(future.channel())));
                        } else {
                            worker.schedule(() -> emitter.onNext(ChannelConnectEvent.fail(new RuntimeException(future.cause()))));
                        }

                        //We're done with the worker, release it to avoid any leaks.
                        worker.schedule(worker::dispose);
                    });

            final CancellableDisposable channelFutureDisposable = new CancellableDisposable(() -> {
                if (channelFuture.isDone()) {
                    if (channelFuture.isSuccess()) {
                        LOG.info("channel={} closing due to downstream dispose.", channelFuture.channel());
                        channelFuture
                                .channel()
                                .close();
                    }
                } else {
                    LOG.info("Cancelling channel future.");
                    channelFuture.cancel(true);
                }
            });

            compositeDisposable.add(channelFutureDisposable);

        }, BackpressureStrategy.BUFFER);
    }

}
