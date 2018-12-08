package com.sibilantsolutions.grison.net.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.rx.ChannelConnectEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.internal.disposables.CancellableDisposable;
import io.reactivex.schedulers.Schedulers;

public class BootstrapBinder {

    private static final Logger LOG = LoggerFactory.getLogger(BootstrapBinder.class);

    public Flowable<ChannelConnectEvent> bind(Bootstrap bootstrap) {
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

            LOG.info("Calling bind on bootstrap={}.", bootstrap);

            final ChannelFuture channelFuture = bootstrap
                    .bind()
                    .addListener((ChannelFutureListener) future -> {

                        if (!future.isDone()) {
                            LOG.error("bind listener fired but not done for bootstrap={}: future={}.", bootstrap, future);

                            worker.schedule(() -> emitter.onNext(ChannelConnectEvent.fail(new RuntimeException("Why did the listener fire if the future isn't done?"))));
                        } else if (future.isSuccess()) {
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
