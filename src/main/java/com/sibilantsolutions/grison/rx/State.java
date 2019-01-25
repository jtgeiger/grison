package com.sibilantsolutions.grison.rx;

import java.util.EnumSet;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.sibilantsolutions.grison.driver.foscam.domain.AlarmTypeE;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.entity.AlarmNotifyTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioStartRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.Unk02TextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoStartRespTextEntity;
import io.netty.channel.Channel;

public class State {

    private static final Logger LOG = LoggerFactory.getLogger(State.class);

    public enum HandshakeState {
        OPERATION_CONNECT_IN_FLIGHT,
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
        AUDIO_START_SENDING,
        AUDIO_START_SENT,
        AUDIO_START_RESPONDED,
        AUDIO_VIDEO_CONNECT_IN_FLIGHT,
        AUDIO_VIDEO_CONNECTED,
        AUDIO_VIDEO_LOGIN_SENDING,
        AUDIO_VIDEO_LOGIN_SENT,
    }

    private static final State INITIAL = new State(null, null, ImmutableSet.of(), null, null, null, null, null, null, null, AlarmState.NONE);

    public final Channel operationChannel;
    public final Throwable failureCause;

    public final ImmutableSet<HandshakeState> handshakeState;

    public final LoginRespTextEntity loginRespText;
    public final VerifyRespTextEntity verifyRespText;

    public final VideoStartRespTextEntity videoStartRespText;
    public final AudioStartRespTextEntity audioStartRespText;

    public final Channel audioVideoChannel;

    public final VideoDataTextEntity videoDataText;
    public final AudioDataTextEntity audioDataText;

    public final AlarmState alarmState;

    private State(Channel operationChannel, Throwable failureCause, ImmutableSet<HandshakeState> handshakeState, LoginRespTextEntity loginRespText, VerifyRespTextEntity verifyRespText, VideoStartRespTextEntity videoStartRespText, AudioStartRespTextEntity audioStartRespText, Channel audioVideoChannel, VideoDataTextEntity videoDataText, AudioDataTextEntity audioDataText, AlarmState alarmState) {
        this.operationChannel = operationChannel;
        this.failureCause = failureCause;
        this.handshakeState = Objects.requireNonNull(handshakeState);
        this.loginRespText = loginRespText;
        this.verifyRespText = verifyRespText;
        this.videoStartRespText = videoStartRespText;
        this.audioStartRespText = audioStartRespText;
        this.audioVideoChannel = audioVideoChannel;
        this.videoDataText = videoDataText;
        this.audioDataText = audioDataText;
        this.alarmState = Objects.requireNonNull(alarmState);
    }

    @Override
    public String toString() {

        return "State{" +
                "failureCause=" + failureCause +
                ", handshakeState=" + handshakeState +
                ", operationChannel=" + operationChannel +
                ", loginRespText=" + loginRespText +
                ", verifyRespText=" + verifyRespText +
                ", videoStartRespText=" + videoStartRespText +
                ", audioStartRespText=" + audioStartRespText +
                ", audioVideoChannel=" + audioVideoChannel +
                ", videoDataText=" + videoDataText +
                ", audioDataText=" + audioDataText +
                ", alarmState=" + alarmState +
                '}';
    }

    public static State fail(Throwable failureCause, State state) {
        LOG.error("State fail={}:", state, failureCause);
        return new State(state.operationChannel, Objects.requireNonNull(failureCause), state.handshakeState, state.loginRespText, state.verifyRespText, state.videoStartRespText, state.audioStartRespText, state.audioVideoChannel, state.videoDataText, state.audioDataText, state.alarmState);
    }

    public static State init() {
        return INITIAL;
    }

    private static ImmutableSet<HandshakeState> add(HandshakeState handshakeState, ImmutableSet<HandshakeState> set) {
        final EnumSet<HandshakeState> enumSet = Sets.newEnumSet(set, HandshakeState.class);
        enumSet.add(handshakeState);
        return Sets.immutableEnumSet(enumSet);
    }

    public static State operationConnectInFlight(State state) {
        if (!state.handshakeState.isEmpty()) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(null, null, add(HandshakeState.OPERATION_CONNECT_IN_FLIGHT, state.handshakeState), null, null, null, null, null, null, null, state.alarmState);
    }

    public static State operationConnected(Channel operationChannel, State state) {
        return new State(Objects.requireNonNull(operationChannel), null, add(HandshakeState.OPERATION_CONNECTED, state.handshakeState), null, null, null, null, null, null, null, state.alarmState);
    }

    public static State loginSending(State state) {
        if (!state.handshakeState.contains(HandshakeState.OPERATION_CONNECTED)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.LOGIN_SENDING, state.handshakeState), null, null, null, null, null, null, null, state.alarmState);
    }

    public static State loginSent(State state) {
        if (!state.handshakeState.contains(HandshakeState.LOGIN_SENDING)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.LOGIN_SENT, state.handshakeState), null, null, null, null, null, null, null, state.alarmState);
    }

    public static State loginRespText(LoginRespTextEntity loginRespText, State state) {
        if (!state.handshakeState.contains(HandshakeState.LOGIN_SENT)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        if (loginRespText.resultCode() != ResultCodeE.CORRECT) {
            throw new RuntimeException("Invalid result=" + loginRespText.resultCode());
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.LOGIN_RESPONDED, state.handshakeState), Objects.requireNonNull(loginRespText), null, null, null, null, null, null, state.alarmState);
    }

    public static State verifySending(State state) {
        if (!state.handshakeState.contains(HandshakeState.LOGIN_RESPONDED)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.VERIFY_SENDING, state.handshakeState), state.loginRespText, null, null, null, null, null, null, state.alarmState);
    }

    public static State verifySent(State state) {
        if (!state.handshakeState.contains(HandshakeState.VERIFY_SENDING)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.VERIFY_SENT, state.handshakeState), state.loginRespText, null, null, null, null, null, null, state.alarmState);
    }

    public static State verifyRespText(VerifyRespTextEntity verifyRespText, State state) {
        if (!state.handshakeState.contains(HandshakeState.VERIFY_SENT)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        if (verifyRespText.resultCode() != ResultCodeE.CORRECT) {
            throw new RuntimeException("Invalid result=" + verifyRespText.resultCode());
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.VERIFY_RESPONDED, state.handshakeState), state.loginRespText, Objects.requireNonNull(verifyRespText), null, null, null, null, null, state.alarmState);
    }

    public static State unk02(Unk02TextEntity unk02Text, State state) {
        if (!state.handshakeState.contains(HandshakeState.VERIFY_SENT)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        LOG.info("{} Handshake completed successfully.", state.operationChannel);

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.UNK02_RECEIVED, state.handshakeState), state.loginRespText, state.verifyRespText, null, null, null, null, null, state.alarmState);
    }

    public static State videoStartSending(State state) {
        if (!state.handshakeState.contains(HandshakeState.UNK02_RECEIVED)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.VIDEO_START_SENDING, state.handshakeState), state.loginRespText, state.verifyRespText, null, null, state.audioVideoChannel, state.videoDataText, state.audioDataText, state.alarmState);
    }

    public static State videoStartSent(State state) {
        if (!state.handshakeState.contains(HandshakeState.VIDEO_START_SENDING)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(state.operationChannel, null, add(HandshakeState.VIDEO_START_SENT, state.handshakeState), state.loginRespText, state.verifyRespText, null, null, state.audioVideoChannel, state.videoDataText, state.audioDataText, state.alarmState);
    }

    public static State videoStartResp(VideoStartRespTextEntity videoStartRespText, State state) {
        if (!state.handshakeState.contains(HandshakeState.VIDEO_START_SENT)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        if (videoStartRespText.result() != ResultCodeE.CORRECT) {
            throw new RuntimeException("Invalid result=" + videoStartRespText.result());
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.VIDEO_START_RESPONDED, state.handshakeState), state.loginRespText, state.verifyRespText, Objects.requireNonNull(videoStartRespText), state.audioStartRespText, state.audioVideoChannel, state.videoDataText, state.audioDataText, state.alarmState);
    }

    public static State audioStartSending(State state) {
        if (!state.handshakeState.contains(HandshakeState.UNK02_RECEIVED)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.AUDIO_START_SENDING, state.handshakeState), state.loginRespText, state.verifyRespText, null, null, state.audioVideoChannel, state.videoDataText, state.audioDataText, state.alarmState);
    }

    public static State audioStartSent(State state) {
        if (!state.handshakeState.contains(HandshakeState.AUDIO_START_SENDING)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.AUDIO_START_SENT, state.handshakeState), state.loginRespText, state.verifyRespText, null, null, state.audioVideoChannel, state.videoDataText, state.audioDataText, state.alarmState);
    }

    public static State audioStartResp(AudioStartRespTextEntity audioStartRespText, State state) {
        if (!state.handshakeState.contains(HandshakeState.AUDIO_START_SENT)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        if (audioStartRespText.result() != ResultCodeE.CORRECT) {
            throw new RuntimeException("Invalid result=" + audioStartRespText.result());
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.AUDIO_START_RESPONDED, state.handshakeState), state.loginRespText, state.verifyRespText, state.videoStartRespText, Objects.requireNonNull(audioStartRespText), state.audioVideoChannel, state.videoDataText, state.audioDataText, state.alarmState);
    }

    public static State audioVideoConnectInFlight(State state) {
        if (!(state.handshakeState.contains(HandshakeState.VIDEO_START_RESPONDED) || state.handshakeState.contains(HandshakeState.AUDIO_START_RESPONDED))) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.AUDIO_VIDEO_CONNECT_IN_FLIGHT, state.handshakeState), state.loginRespText, state.verifyRespText, state.videoStartRespText, state.audioStartRespText, null, null, null, state.alarmState);
    }

    public static State audioVideoConnected(Channel audioVideoChannel, State state) {
        if (!state.handshakeState.contains(HandshakeState.AUDIO_VIDEO_CONNECT_IN_FLIGHT)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(
                Objects.requireNonNull(state.operationChannel),
                null,
                add(HandshakeState.AUDIO_VIDEO_CONNECTED, state.handshakeState),
                Objects.requireNonNull(state.loginRespText),
                Objects.requireNonNull(state.verifyRespText),
//                Objects.requireNonNull(state.videoStartRespText),
                state.videoStartRespText,
                state.audioStartRespText,
                Objects.requireNonNull(audioVideoChannel),
                null,
                null,
                state.alarmState);
    }

    public static State audioVideoLoginSending(State state) {
        if (!state.handshakeState.contains(HandshakeState.AUDIO_VIDEO_CONNECTED)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.AUDIO_VIDEO_LOGIN_SENDING, state.handshakeState), state.loginRespText, state.verifyRespText, state.videoStartRespText, state.audioStartRespText, Objects.requireNonNull(state.audioVideoChannel), null, null, state.alarmState);
    }

    public static State audioVideoLoginSent(State state) {
        if (!state.handshakeState.contains(HandshakeState.AUDIO_VIDEO_LOGIN_SENDING)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.AUDIO_VIDEO_LOGIN_SENT, state.handshakeState), state.loginRespText, state.verifyRespText, state.videoStartRespText, state.audioStartRespText, Objects.requireNonNull(state.audioVideoChannel), null, null, state.alarmState);
    }

    public static State videoDataText(VideoDataTextEntity videoDataText, State state) {
        if (!state.handshakeState.contains(HandshakeState.AUDIO_VIDEO_LOGIN_SENT)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.AUDIO_VIDEO_LOGIN_SENT, state.handshakeState), state.loginRespText, state.verifyRespText, state.videoStartRespText, state.audioStartRespText, Objects.requireNonNull(state.audioVideoChannel), Objects.requireNonNull(videoDataText), state.audioDataText, state.alarmState);
    }

    public static State audioDataText(AudioDataTextEntity audioDataText, State state) {
        if (!state.handshakeState.contains(HandshakeState.AUDIO_VIDEO_LOGIN_SENT)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        return new State(Objects.requireNonNull(state.operationChannel), null, add(HandshakeState.AUDIO_VIDEO_LOGIN_SENT, state.handshakeState), state.loginRespText, state.verifyRespText, state.videoStartRespText, state.audioStartRespText, Objects.requireNonNull(state.audioVideoChannel), state.videoDataText, Objects.requireNonNull(audioDataText), state.alarmState);
    }

    public static State alarmNotify(AlarmNotifyTextEntity alarmNotifyText, State state) {
        if (!state.handshakeState.contains(HandshakeState.VERIFY_RESPONDED)) {
            throw new IllegalStateException(String.format("handshakeState=%s", state.handshakeState));
        }

        final AlarmState alarmState = alarmState(alarmNotifyText.alarmType());

        return new State(Objects.requireNonNull(state.operationChannel), null, state.handshakeState, state.loginRespText, state.verifyRespText, state.videoStartRespText, state.audioStartRespText, state.audioVideoChannel, state.videoDataText, state.audioDataText, alarmState);
    }

    private static AlarmState alarmState(AlarmTypeE alarmType) {
        switch (alarmType) {
            case ALARM_STOP:
                return AlarmState.ALARM_STOP;

            case MOTION_DETECTION:
                return AlarmState.MOTION_DETECTION;

            case OUTSIDE_ALARM:
                return AlarmState.OUTSIDE_ALARM;

            case SOUND_DETECTION:
                return AlarmState.SOUND_DETECTION;

            default:
                throw new IllegalArgumentException(String.format("Unexpected alarm type=%s", alarmType));
        }
    }

}
