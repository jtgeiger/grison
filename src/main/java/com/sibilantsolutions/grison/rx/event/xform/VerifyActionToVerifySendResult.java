package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.rx.ChannelSender;
import com.sibilantsolutions.grison.rx.OpClient;
import com.sibilantsolutions.grison.rx.OpClientImpl;
import com.sibilantsolutions.grison.rx.event.action.VerifyAction;
import com.sibilantsolutions.grison.rx.event.result.VerifySendResult;
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
                            .<VerifySendResult>toFlowable()
                            .startWith(Flowable.just(VerifySendResult.IN_FLIGHT))
                            .onErrorReturn(VerifySendResult::new);
                });
    }
}
