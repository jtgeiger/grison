package com.sibilantsolutions.grison.rx.event.result;

public class AudioStartSendResult extends AbstractResult {

    public static final AudioStartSendResult IN_FLIGHT = new AudioStartSendResult(null);
    public static final AudioStartSendResult SENT = new AudioStartSendResult(null);

    public final Throwable failureCause;

    public AudioStartSendResult(Throwable failureCause) {
        this.failureCause = failureCause;
    }

}
