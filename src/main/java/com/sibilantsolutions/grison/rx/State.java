package com.sibilantsolutions.grison.rx;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.Unk02TextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoStartRespTextEntity;
import io.netty.channel.Channel;

public class State {

    private static final Logger LOG = LoggerFactory.getLogger(State.class);

    public enum HandshakeState {
        INIT,
        OPERATION_CONNECTED,
        LOGIN_SENDING,
        LOGIN_SENT,
        LOGIN_RESPONDED,
        VERIFY_SENDING,
        VERIFY_SENT,
        VERIFY_RESPONDED,
        UNK02_RECEIVED,
        VIDEO_START_SENDING,
        VIDEO_START_SENT,
        VIDEO_START_RESPONDED,
        AUDIO_VIDEO_CONNECT_IN_FLIGHT,
        AUDIO_VIDEO_CONNECTED,
        AUDIO_VIDEO_LOGIN_SENDING,
        AUDIO_VIDEO_LOGIN_SENT,
    }

    public static final State INITIAL = new State(null, null, HandshakeState.INIT, null, null, null, null, null);
    public static final State OP_CONNECT_IN_FLIGHT = new State(null, null, HandshakeState.INIT, null, null, null, null, null);

    public final Channel operationChannel;
    public final Throwable failureCause;

    public final HandshakeState handshakeState;

    public final LoginRespTextEntity loginRespText;
    public final VerifyRespTextEntity verifyRespText;

    public final VideoStartRespTextEntity videoStartRespText;

    public final Channel audioVideoChannel;

    public final VideoDataTextEntity videoDataText;

    private State(Channel operationChannel, Throwable failureCause, HandshakeState handshakeState, LoginRespTextEntity loginRespText, VerifyRespTextEntity verifyRespText, VideoStartRespTextEntity videoStartRespText, Channel audioVideoChannel, VideoDataTextEntity videoDataText) {
        this.operationChannel = operationChannel;
        this.failureCause = failureCause;
        this.handshakeState = Objects.requireNonNull(handshakeState);
        this.loginRespText = loginRespText;
        this.verifyRespText = verifyRespText;
        this.videoStartRespText = videoStartRespText;
        this.audioVideoChannel = audioVideoChannel;
        this.videoDataText = videoDataText;
    }

    @Override
    public String toString() {
        if (this == INITIAL) {
            return "State{INITIAL}";
        }
        if (this == OP_CONNECT_IN_FLIGHT) {
            return "State{OP_CONNECT_IN_FLIGHT}";
        }
        return "State{" +
                "operationChannel=" + operationChannel +
                ", failureCause=" + failureCause +
                ", handshakeState=" + handshakeState +
                ", loginRespText=" + loginRespText +
                ", verifyRespText=" + verifyRespText +
                ", videoStartRespText=" + videoStartRespText +
                ", audioVideoChannel=" + audioVideoChannel +
                ", videoDataText=" + videoDataText +
                '}';
    }

    public static State loginRespText(LoginRespTextEntity loginRespText, State state) {
        if (state.handshakeState != HandshakeState.LOGIN_SENT) {
            throw new IllegalStateException("" + state.handshakeState);
        }

        if (loginRespText.resultCode() != ResultCodeE.CORRECT) {
            throw new RuntimeException("Invalid result=" + loginRespText.resultCode());
        }

        return new State(state.operationChannel, null, HandshakeState.LOGIN_RESPONDED, Objects.requireNonNull(loginRespText), null, null, null, null);
    }

    public static State loginSending(State state) {
        if (state.handshakeState != HandshakeState.OPERATION_CONNECTED) {
            throw new IllegalStateException("" + state.handshakeState);
        }
        return new State(state.operationChannel, null, HandshakeState.LOGIN_SENDING, null, null, null, null, null);
    }

    public static State loginSent(State state) {
        if (state.handshakeState != HandshakeState.LOGIN_SENDING) {
            throw new IllegalStateException("" + state.handshakeState);
        }
        return new State(state.operationChannel, null, HandshakeState.LOGIN_SENT, null, null, null, null, null);
    }

    public static State verifySending(State state) {
        if (state.handshakeState != HandshakeState.LOGIN_RESPONDED) {
            throw new IllegalStateException("" + state.handshakeState);
        }
        return new State(state.operationChannel, null, HandshakeState.VERIFY_SENDING, state.loginRespText, null, null, null, null);
    }

    public static State verifySent(State state) {
        if (state.handshakeState != HandshakeState.VERIFY_SENDING) {
            throw new IllegalStateException("" + state.handshakeState);
        }
        return new State(state.operationChannel, null, HandshakeState.VERIFY_SENT, state.loginRespText, null, null, null, null);
    }

    public static State verifyRespText(VerifyRespTextEntity verifyRespText, State state) {
        if (state.handshakeState != HandshakeState.VERIFY_SENT) {
            throw new IllegalStateException("" + state.handshakeState);
        }

        if (verifyRespText.resultCode() != ResultCodeE.CORRECT) {
            throw new RuntimeException("Invalid result=" + verifyRespText.resultCode());
        }

        return new State(state.operationChannel, null, HandshakeState.VERIFY_RESPONDED, state.loginRespText, Objects.requireNonNull(verifyRespText), null, null, null);
    }

    public static State unk02(Unk02TextEntity unk02Text, State state) {
        if (state.handshakeState != HandshakeState.VERIFY_RESPONDED) {
            throw new IllegalStateException("" + state.handshakeState);
        }

        LOG.info("{} Handshake completed successfully.", state.operationChannel);

        return new State(state.operationChannel, null, HandshakeState.UNK02_RECEIVED, state.loginRespText, state.verifyRespText, null, null, null);
    }

    public static State videoStartSending(State state) {
        if (state.handshakeState != HandshakeState.UNK02_RECEIVED) {
            throw new IllegalStateException("" + state.handshakeState);
        }

        return new State(state.operationChannel, null, HandshakeState.VIDEO_START_SENDING, state.loginRespText, state.verifyRespText, null, state.audioVideoChannel, state.videoDataText);
    }

    public static State videoStartSent(State state) {
        if (state.handshakeState != HandshakeState.VIDEO_START_SENDING) {
            throw new IllegalStateException("" + state.handshakeState);
        }

        return new State(state.operationChannel, null, HandshakeState.VIDEO_START_SENT, state.loginRespText, state.verifyRespText, null, state.audioVideoChannel, state.videoDataText);
    }

    public static State videoStartResp(VideoStartRespTextEntity videoStartRespText, State state) {
        if (state.handshakeState != HandshakeState.VIDEO_START_SENT) {
            throw new IllegalStateException("" + state.handshakeState);
        }

        if (videoStartRespText.result() != ResultCodeE.CORRECT) {
            throw new RuntimeException("Invalid result=" + videoStartRespText.result());
        }

        return new State(state.operationChannel, null, HandshakeState.VIDEO_START_RESPONDED, state.loginRespText, state.verifyRespText, Objects.requireNonNull(videoStartRespText), state.audioVideoChannel, state.videoDataText);
    }

    public static State operationConnected(Channel operationChannel) {
        return new State(Objects.requireNonNull(operationChannel), null, HandshakeState.OPERATION_CONNECTED, null, null, null, null, null);
    }

    public static State fail(Throwable failureCause, State state) {
        LOG.error("State fail={}:", state, failureCause);
        return new State(state.operationChannel, Objects.requireNonNull(failureCause), state.handshakeState, state.loginRespText, state.verifyRespText, state.videoStartRespText, state.audioVideoChannel, state.videoDataText);
    }

    public static State audioVideoConnectInFlight(State state) {
        if (state.handshakeState != HandshakeState.VIDEO_START_RESPONDED) {
            throw new IllegalStateException("" + state.handshakeState);
        }

        return new State(state.operationChannel, null, HandshakeState.AUDIO_VIDEO_CONNECT_IN_FLIGHT, state.loginRespText, state.verifyRespText, state.videoStartRespText, null, null);
    }

    public static State audioVideoConnected(Channel audioVideoChannel, State state) {
        if (state.handshakeState != HandshakeState.AUDIO_VIDEO_CONNECT_IN_FLIGHT) {
            throw new IllegalStateException("" + state.handshakeState);
        }

        return new State(
                Objects.requireNonNull(state.operationChannel),
                null,
                HandshakeState.AUDIO_VIDEO_CONNECTED,
                Objects.requireNonNull(state.loginRespText),
                Objects.requireNonNull(state.verifyRespText),
                Objects.requireNonNull(state.videoStartRespText),
                Objects.requireNonNull(audioVideoChannel),
                null);
    }

    public static State audioVideoLoginSending(State state) {
        if (state.handshakeState != HandshakeState.AUDIO_VIDEO_CONNECTED) {
            throw new IllegalStateException("" + state.handshakeState);
        }

        return new State(state.operationChannel, null, HandshakeState.AUDIO_VIDEO_LOGIN_SENDING, state.loginRespText, state.verifyRespText, state.videoStartRespText, state.audioVideoChannel, null);
    }

    public static State audioVideoLoginSent(State state) {
        if (state.handshakeState != HandshakeState.AUDIO_VIDEO_LOGIN_SENDING) {
            throw new IllegalStateException("" + state.handshakeState);
        }

        return new State(state.operationChannel, null, HandshakeState.AUDIO_VIDEO_LOGIN_SENT, state.loginRespText, state.verifyRespText, state.videoStartRespText, state.audioVideoChannel, null);
    }

    public static State videoDataText(VideoDataTextEntity videoDataText, State state) {
        if (state.handshakeState != HandshakeState.AUDIO_VIDEO_LOGIN_SENT) {
            throw new IllegalStateException("" + state.handshakeState);
        }

        return new State(state.operationChannel, null, HandshakeState.AUDIO_VIDEO_LOGIN_SENT, state.loginRespText, state.verifyRespText, state.videoStartRespText, state.audioVideoChannel, Objects.requireNonNull(videoDataText));
    }

}
