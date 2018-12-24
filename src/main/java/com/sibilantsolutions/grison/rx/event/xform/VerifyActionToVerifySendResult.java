package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.rx.client.OpClient;
import com.sibilantsolutions.grison.rx.client.OpClientImpl;
import com.sibilantsolutions.grison.rx.event.action.VerifyAction;
import com.sibilantsolutions.grison.rx.event.result.VerifySendResult;
import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import com.sibilantsolutions.grison.rx.net.ChannelSender;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class VerifyActionToVerifySendResult implements FlowableTransformer<VerifyAction, VerifySendResult> {
    @Override
    public Publisher<VerifySendResult> apply(Flowable<VerifyAction> upstream) {
        return upstream
                .flatMap(verifyAction -> {
                    final OpClient opClient = new OpClientImpl(new ChannelSender(verifyAction.channel));

                    return opClient
                            .verify(verifyAction.username, verifyAction.password)
                            .map(channelSendEvent -> {
                                if (channelSendEvent == ChannelSendEvent.IN_FLIGHT) {
                                    return VerifySendResult.IN_FLIGHT;
                                } else if (channelSendEvent == ChannelSendEvent.SENT) {
                                    return VerifySendResult.SENT;
                                } else {
                                    return new VerifySendResult(new RuntimeException(channelSendEvent.failureCause));
                                }
                            });
                });
    }
}
