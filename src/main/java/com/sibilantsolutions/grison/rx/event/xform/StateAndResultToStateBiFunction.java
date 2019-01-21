package com.sibilantsolutions.grison.rx.event.xform;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Optional;

import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.VerifyException;
import com.sibilantsolutions.grison.driver.foscam.entity.AlarmNotifyTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioStartRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.Unk02TextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoStartRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.rx.State;
import com.sibilantsolutions.grison.rx.event.action.AbstractAction;
import com.sibilantsolutions.grison.rx.event.action.AudioStartAction;
import com.sibilantsolutions.grison.rx.event.action.AudioVideoConnectAction;
import com.sibilantsolutions.grison.rx.event.action.AudioVideoLoginAction;
import com.sibilantsolutions.grison.rx.event.action.LoginAction;
import com.sibilantsolutions.grison.rx.event.action.VerifyAction;
import com.sibilantsolutions.grison.rx.event.action.VideoStartAction;
import com.sibilantsolutions.grison.rx.event.result.AbstractResult;
import com.sibilantsolutions.grison.rx.event.result.AudioStartSendResult;
import com.sibilantsolutions.grison.rx.event.result.AudioVideoConnectResult;
import com.sibilantsolutions.grison.rx.event.result.AudioVideoLoginSendResult;
import com.sibilantsolutions.grison.rx.event.result.AudioVideoReceiveResult;
import com.sibilantsolutions.grison.rx.event.result.LoginSendResult;
import com.sibilantsolutions.grison.rx.event.result.OperationConnectResult;
import com.sibilantsolutions.grison.rx.event.result.OperationReceiveResult;
import com.sibilantsolutions.grison.rx.event.result.VerifySendResult;
import com.sibilantsolutions.grison.rx.event.result.VideoStartSendResult;
import io.reactivex.functions.BiFunction;

/**
 * Given a State and an AbstractResult, determine and return the new State.
 */
public class StateAndResultToStateBiFunction implements BiFunction<State, AbstractResult, State> {

    private static final Logger LOG = LoggerFactory.getLogger(StateAndResultToStateBiFunction.class);

    private final Subscriber<AbstractAction> dynamicActions;
    private final String username;
    private final String password;

    public StateAndResultToStateBiFunction(Subscriber<AbstractAction> dynamicActions, String username, String password) {
        this.dynamicActions = dynamicActions;
        this.username = username;
        this.password = password;
    }

    @Override
    public State apply(State state, AbstractResult result) {

        if (result == OperationConnectResult.IN_FLIGHT) {
            return State.operationConnectInFlight(state);
        }

        if (result instanceof OperationConnectResult) {
            OperationConnectResult cr = (OperationConnectResult) result;
            if (cr.channel != null) {
                dynamicActions.onNext(new LoginAction(cr.channel));

                return State.operationConnected(cr.channel, state);
            } else {
                return State.fail(new RuntimeException(cr.failureCause), state);
            }
        }

        if (result instanceof LoginSendResult) {
            LoginSendResult lsr = (LoginSendResult) result;
            if (lsr == LoginSendResult.IN_FLIGHT) {
                return State.loginSending(state);
            } else if (lsr == LoginSendResult.SENT) {
                return State.loginSent(state);
            } else {
                return State.fail(lsr.failureCause, state);
            }
        }

        if (result instanceof VerifySendResult) {
            VerifySendResult vsr = (VerifySendResult) result;
            if (vsr == VerifySendResult.IN_FLIGHT) {
                return State.verifySending(state);
            } else if (vsr == VerifySendResult.SENT) {
                return State.verifySent(state);
            } else {
                return State.fail(vsr.failureCause, state);
            }
        }

        if (result instanceof VideoStartSendResult) {
            VideoStartSendResult vsr = (VideoStartSendResult) result;
            if (vsr == VideoStartSendResult.IN_FLIGHT) {
                return State.videoStartSending(state);
            } else if (vsr == VideoStartSendResult.SENT) {
                return State.videoStartSent(state);
            } else {
                return State.fail(vsr.failureCause, state);
            }
        }

        if (result instanceof AudioStartSendResult) {
            AudioStartSendResult asr = (AudioStartSendResult) result;
            if (asr == AudioStartSendResult.IN_FLIGHT) {
                return State.audioStartSending(state);
            } else if (asr == AudioStartSendResult.SENT) {
                return State.audioStartSent(state);
            } else {
                return State.fail(asr.failureCause, state);
            }
        }

        if (result instanceof OperationReceiveResult) {
            OperationReceiveResult r = (OperationReceiveResult) result;

            if (r.text() instanceof LoginRespTextEntity) {
                final State state1 = State.loginRespText((LoginRespTextEntity) r.text(), state);
                dynamicActions.onNext(new VerifyAction(state1.operationChannel, username, password));
                return state1;
            }

            if (r.text() instanceof VerifyRespTextEntity) {
                return State.verifyRespText((VerifyRespTextEntity) r.text(), state);
            }

            if (r.text() instanceof Unk02TextEntity) {
                final State state1 = State.unk02((Unk02TextEntity) r.text(), state);
                dynamicActions.onNext(new VideoStartAction(state1.operationChannel));
                dynamicActions.onNext(new AudioStartAction(state1.operationChannel));
                return state1;
            }

            if (r.text() instanceof VideoStartRespTextEntity) {
                final State state1 = State.videoStartResp((VideoStartRespTextEntity) r.text(), state);
                return maybeConnect(state1.videoStartRespText.dataConnectionId(), state1, dynamicActions);
            }

            if (r.text() instanceof AudioStartRespTextEntity) {
                final State state1 = State.audioStartResp((AudioStartRespTextEntity) r.text(), state);
                return maybeConnect(state1.audioStartRespText.dataConnectionId(), state1, dynamicActions);
            }

            if (r.text() instanceof AlarmNotifyTextEntity) {
                return State.alarmNotify((AlarmNotifyTextEntity) r.text(), state);
            }

            throw new IllegalArgumentException("Unexpected handshake state=" + state + ", result=" + result);
        }

        if (result instanceof AudioVideoConnectResult) {
            AudioVideoConnectResult r = (AudioVideoConnectResult) result;
            LOG.info("AudioVideoConnectResult={}.", r);
            if (r == AudioVideoConnectResult.IN_FLIGHT) {
                return State.audioVideoConnectInFlight(state);
            }
            final State state1;
            if (r.channel != null) {
                state1 = State.audioVideoConnected(r.channel, state);

                Optional<FosInt32> o = (state1.videoStartRespText != null
                        ? state1.videoStartRespText.dataConnectionId()
                        : state1.audioStartRespText.dataConnectionId());
                final FosInt32 dataConnectionId = o.orElseThrow(VerifyException::new);

                final AudioVideoLoginAction audioVideoLoginAction = new AudioVideoLoginAction(state1.audioVideoChannel, dataConnectionId);
                dynamicActions.onNext(audioVideoLoginAction);

            } else {
                state1 = State.fail(r.failureCause, state);
            }
            return state1;
        }

        if (result instanceof AudioVideoLoginSendResult) {
            AudioVideoLoginSendResult r = (AudioVideoLoginSendResult) result;
            LOG.info("AudioVideoLoginSendResult={}.", r);

            if (r == AudioVideoLoginSendResult.IN_FLIGHT) {
                return State.audioVideoLoginSending(state);
            } else if (r == AudioVideoLoginSendResult.SENT) {
                return State.audioVideoLoginSent(state);
            } else {
                return State.fail(new RuntimeException(r.failureCause), state);
            }
        }

        if (result instanceof AudioVideoReceiveResult) {
            AudioVideoReceiveResult r = (AudioVideoReceiveResult) result;

            if (r.text() instanceof VideoDataTextEntity) {
                VideoDataTextEntity t = (VideoDataTextEntity) r.text();
                return State.videoDataText(t, state);
            }

            if (r.text() instanceof AudioDataTextEntity) {
                AudioDataTextEntity t = (AudioDataTextEntity) r.text();
                return State.audioDataText(t, state);
            }

            throw new IllegalArgumentException("Unexpected result=" + result + " with state=" + state);

        }

        throw new IllegalArgumentException("Unexpected result=" + result + " with state=" + state);
    }

    /**
     * Fire the AudioVideoConnectAction if a dataConnectionId is present.  If not that means that the audio/video connection is already established.
     *
     * @param dataConnectionId Optional dataConnectionId that came with videoStartRespText or audioStartRespText, if any.
     * @param state1           State to return after the action has (maybe) been fired.
     * @param dynamicActions   Subscriber to which to fire the action.
     * @return The new state.
     */
    private static State maybeConnect(Optional<FosInt32> dataConnectionId, State state1, Subscriber<AbstractAction> dynamicActions) {
        return dataConnectionId
                .map(ignored -> {
                    final SocketAddress socketAddress = state1.operationChannel.remoteAddress();
                    if (socketAddress instanceof InetSocketAddress) {
                        InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
                        dynamicActions.onNext(new AudioVideoConnectAction(inetSocketAddress.getHostString(), inetSocketAddress.getPort()));
                        return state1;
                    } else {
                        return State.fail(new RuntimeException("Expected " + InetSocketAddress.class.getSimpleName() + " but got=" + socketAddress), state1);
                    }
                })
                .orElse(state1);
    }

}
