package com.sibilantsolutions.grison.rx.event.xform;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.reactivestreams.Publisher;

import com.google.common.base.VerifyException;
import com.sibilantsolutions.grison.rx.State;
import com.sibilantsolutions.grison.rx.event.action.AbstractAction;
import com.sibilantsolutions.grison.rx.event.action.AudioVideoConnectAction;
import com.sibilantsolutions.grison.rx.event.action.AudioVideoLoginAction;
import com.sibilantsolutions.grison.rx.event.action.LoginAction;
import com.sibilantsolutions.grison.rx.event.action.VerifyAction;
import com.sibilantsolutions.grison.rx.event.action.VideoStartAction;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.processors.FlowableProcessor;

public class StateToState implements FlowableTransformer<State, State> {

    private final FlowableProcessor<AbstractAction> dynamicActions;
    private final String username;
    private final String password;

    public StateToState(FlowableProcessor<AbstractAction> dynamicActions, String username, String password) {
        this.dynamicActions = dynamicActions;
        this.username = username;
        this.password = password;
    }

    @Override
    public Publisher<State> apply(Flowable<State> upstream) {

        return upstream
                .flatMap(state -> {

                    switch (state.handshakeState) {
                        case OPERATION_CONNECTED:
                            dynamicActions.onNext(new LoginAction(state.operationChannel));
                            break;

                        case LOGIN_RESPONDED:
                            dynamicActions.onNext(new VerifyAction(state.operationChannel, username, password));
                            break;

                        case UNK02_RECEIVED:
                            dynamicActions.onNext(new VideoStartAction(state.operationChannel));
                            break;

                        case VIDEO_START_RESPONDED:
                            final SocketAddress socketAddress = state.operationChannel.remoteAddress();
                            if (socketAddress instanceof InetSocketAddress) {
                                InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
                                dynamicActions.onNext(new AudioVideoConnectAction(inetSocketAddress.getHostString(), inetSocketAddress.getPort()));
                            } else {
                                return Flowable.just(State.fail(new RuntimeException("Expected " + InetSocketAddress.class.getSimpleName() + " but got=" + socketAddress), state));
                            }
                            break;

                        case AUDIO_VIDEO_CONNECTED:
                            final AudioVideoLoginAction audioVideoLoginAction = new AudioVideoLoginAction(state.audioVideoChannel, state.videoStartRespText.dataConnectionId().orElseThrow(VerifyException::new));
                            dynamicActions.onNext(audioVideoLoginAction);
                            break;
                    }

                    return Flowable.just(state);
                });
    }
}
