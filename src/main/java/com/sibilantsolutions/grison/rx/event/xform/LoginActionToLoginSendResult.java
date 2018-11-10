package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.rx.ChannelSender;
import com.sibilantsolutions.grison.rx.OpClient;
import com.sibilantsolutions.grison.rx.OpClientImpl;
import com.sibilantsolutions.grison.rx.event.action.LoginAction;
import com.sibilantsolutions.grison.rx.event.result.LoginSendResult;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class LoginActionToLoginSendResult implements FlowableTransformer<LoginAction, LoginSendResult> {
    @Override
    public Publisher<LoginSendResult> apply(Flowable<LoginAction> upstream) {
        final Flowable<OpClient> opClientFlowable = upstream
                .map(loginAction -> loginAction.channel)
                .map(ChannelSender::new)
                .map(OpClientImpl::new);

        return opClientFlowable
                .flatMap(opClient -> opClient
                        .login()
                        .<LoginSendResult>toFlowable()
                        .startWith(Flowable.just(LoginSendResult.IN_FLIGHT))
                        .onErrorReturn(LoginSendResult::new)
                );
    }
}
