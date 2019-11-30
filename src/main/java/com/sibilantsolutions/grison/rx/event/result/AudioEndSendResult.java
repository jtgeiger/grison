package com.sibilantsolutions.grison.rx.event.result;

public class AudioEndSendResult extends AbstractResult {

    public static final AudioEndSendResult IN_FLIGHT = new AudioEndSendResult(null);
    public static final AudioEndSendResult SENT = new AudioEndSendResult(null);

    public final Throwable failureCause;

    public AudioEndSendResult(Throwable failureCause) {
        this.failureCause = failureCause;
    }

}
