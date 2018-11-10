package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.rx.AvClient;
import com.sibilantsolutions.grison.rx.AvClientImpl;
import com.sibilantsolutions.grison.rx.ChannelSender;
import com.sibilantsolutions.grison.rx.event.action.AudioVideoLoginAction;
import com.sibilantsolutions.grison.rx.event.result.AudioVideoLoginSendResult;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class AudioVideoLoginActionToAudioVideoLoginSendResult implements FlowableTransformer<AudioVideoLoginAction, AudioVideoLoginSendResult> {

    private static final Logger LOG = LoggerFactory.getLogger(AudioVideoLoginActionToAudioVideoLoginSendResult.class);

    @Override
    public Publisher<AudioVideoLoginSendResult> apply(Flowable<AudioVideoLoginAction> upstream) {
        return upstream
                .flatMap(audioVideoLoginAction -> {
                    final AvClient avClient = new AvClientImpl(new ChannelSender(audioVideoLoginAction.channel));

                    return avClient
                            .audioVideoLogin(audioVideoLoginAction.dataConnectionId)
                            .andThen(Completable.fromCallable(() -> {
                                LOG.info("Replacing AudioVideo logger with trace-level logger.");
                                audioVideoLoginAction.channel
                                        .pipeline()
                                        //Log lifecycle events AND datastream ("io.netty.handler.logging.LoggingHandler"; TRACE level).
                                        //Everything that follows is very high frequency so don't want to stay at DEBUG level.
                                        .replace(LoggingHandler.class, "traceLogger", new LoggingHandler(LogLevel.TRACE));
                                return Completable.complete();
                            }))
                            .<AudioVideoLoginSendResult>toFlowable()
                            .startWith(Flowable.just(AudioVideoLoginSendResult.IN_FLIGHT))
                            .onErrorReturn(AudioVideoLoginSendResult::new);
                });
    }
}
