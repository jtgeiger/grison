package com.sibilantsolutions.grison.rx.event.xform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.LoginRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.Unk02Text;
import com.sibilantsolutions.grison.driver.foscam.domain.VerifyRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoStartRespText;
import com.sibilantsolutions.grison.rx.State;
import com.sibilantsolutions.grison.rx.event.result.AbstractResult;
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

    @Override
    public State apply(State state, AbstractResult result) throws Exception {
        if (state == State.INITIAL && result == OperationConnectResult.IN_FLIGHT) {
            return State.OP_CONNECT_IN_FLIGHT;
        } else if (state == State.OP_CONNECT_IN_FLIGHT && result instanceof OperationConnectResult) {
            OperationConnectResult cr = (OperationConnectResult) result;
            if (cr.channel != null) {
                return State.operationConnected(cr.channel);
            } else {
                return State.fail(new RuntimeException(cr.failureCause), state);
            }
        } else if (state.operationChannel != null && result instanceof LoginSendResult) {
            LoginSendResult lsr = (LoginSendResult) result;
            if (lsr == LoginSendResult.IN_FLIGHT) {
                return State.loginSent(state);
            } else {
                return State.fail(lsr.failureCause, state);
            }
        } else if (state.operationChannel != null && result instanceof VerifySendResult) {
            VerifySendResult vsr = (VerifySendResult) result;
            if (vsr == VerifySendResult.IN_FLIGHT) {
                return State.verifySent(state);
            } else {
                return State.fail(vsr.failureCause, state);
            }
        } else if (state.operationChannel != null && result instanceof VideoStartSendResult) {
            VideoStartSendResult vsr = (VideoStartSendResult) result;
            if (vsr == VideoStartSendResult.IN_FLIGHT) {
                return State.videoStartSent(state);
            } else {
                return State.fail(vsr.failureCause, state);
            }
        } else if (result instanceof AudioVideoConnectResult) {
            AudioVideoConnectResult r = (AudioVideoConnectResult) result;
            LOG.info("AudioVideoConnectResult={}.", r);
            if (r == AudioVideoConnectResult.IN_FLIGHT) {
                return State.audioVideoConnectInFlight(state);
            }
            final State state1;
            if (r.channel != null) {
                state1 = State.audioVideoConnected(r.channel, state);
            } else {
                state1 = State.fail(r.failureCause, state);
            }
            return state1;
        } else if (result instanceof AudioVideoLoginSendResult) {
            AudioVideoLoginSendResult r = (AudioVideoLoginSendResult) result;
            LOG.info("AudioVideoLoginSendResult={}.", r);

            if (r == AudioVideoLoginSendResult.IN_FLIGHT) {
                return State.audioVideoLoginSent(state);
            } else {
                return State.fail(new RuntimeException(r.failureCause), state);
            }
        } else if (state.operationChannel != null && result instanceof OperationReceiveResult) {
            OperationReceiveResult r = (OperationReceiveResult) result;

            switch (state.handshakeState) {
                case LOGIN_SENT:
                    final State state1 = State.loginRespText((LoginRespText) r.command.getCommandText(), state);
                    return state1;

                case VERIFY_SENT:
                    return State.verifyRespText((VerifyRespText) r.command.getCommandText(), state);

                case VERIFY_RESPONDED:
                    final State state2 = State.unk02((Unk02Text) r.command.getCommandText(), state);
                    return state2;

                case VIDEO_START_SENT:
                    return State.videoStartResp((VideoStartRespText) r.command.getCommandText(), state);

                default:
                    throw new IllegalArgumentException("Unexpected handshake state=" + state.handshakeState);
            }
        } else if (state.handshakeState == State.HandshakeState.AUDIO_VIDEO_LOGIN_SENT && result instanceof AudioVideoReceiveResult) {
            AudioVideoReceiveResult r = (AudioVideoReceiveResult) result;

            if (r.command.getCommandText() instanceof VideoDataText) {
                VideoDataText t = (VideoDataText) r.command.getCommandText();
                return State.videoDataText(t, state);
            }

            throw new UnsupportedOperationException("TODO");

        }

        throw new IllegalArgumentException("Unexpected result=" + result + " with state=" + state);
    }
}
