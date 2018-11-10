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

public class BootstrapConnector {

    private static final Logger LOG = LoggerFactory.getLogger(BootstrapConnector.class);

    public Flowable<ChannelConnectEvent> connect(Bootstrap bootstrap) {
        return Flowable.create(emitter -> {

            final Scheduler.Worker worker = Schedulers.io().createWorker();

            worker.schedule(() -> emitter.onNext(ChannelConnectEvent.IN_FLIGHT));

            LOG.info("Calling connect on bootstrap={}.", bootstrap);

            final ChannelFuture channelFuture = bootstrap
                    .connect()
                    .addListener((ChannelFutureListener) future -> {

                        if (!future.isDone()) {
                            LOG.error("connect listener fired but not done for bootstrap={}: future={}.", bootstrap, future);

                            worker.schedule(() -> emitter.onNext(ChannelConnectEvent.fail(new RuntimeException("Why did the listener fire if the future isn't done?"))));
                        } else if (future.isSuccess()) {
                            worker.schedule(() -> emitter.onNext(ChannelConnectEvent.success(future.channel())));
                        } else {
                            worker.schedule(() -> emitter.onNext(ChannelConnectEvent.fail(new RuntimeException(future.cause()))));
                        }

                        worker.schedule(emitter::onComplete);

                        //We're done with the worker, release it to avoid any leaks.
                        worker.dispose();
                    });

            emitter.setDisposable(new CompositeDisposable(
                    new CancellableDisposable(() -> {
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
                    }),
                    worker)
            );
        }, BackpressureStrategy.BUFFER);
    }

}
