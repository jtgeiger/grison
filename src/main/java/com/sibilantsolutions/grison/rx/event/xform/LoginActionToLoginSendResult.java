package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.rx.client.OpClient;
import com.sibilantsolutions.grison.rx.client.OpClientImpl;
import com.sibilantsolutions.grison.rx.event.action.LoginAction;
import com.sibilantsolutions.grison.rx.event.result.LoginSendResult;
import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import com.sibilantsolutions.grison.rx.net.ChannelSender;
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
                        .map(channelSendEvent -> {
                            if (channelSendEvent == ChannelSendEvent.IN_FLIGHT) {
                                return LoginSendResult.IN_FLIGHT;
                            } else if (channelSendEvent == ChannelSendEvent.SENT) {
                                return LoginSendResult.SENT;
                            } else {
                                return new LoginSendResult(new RuntimeException(channelSendEvent.failureCause));
                            }
                        })
                );
    }
}
