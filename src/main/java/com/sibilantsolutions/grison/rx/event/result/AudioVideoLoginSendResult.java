package com.sibilantsolutions.grison.rx.event.result;

public class AudioVideoLoginSendResult extends AbstractResult {
    public static final AudioVideoLoginSendResult IN_FLIGHT = new AudioVideoLoginSendResult(null);
    public static final AudioVideoLoginSendResult SENT = new AudioVideoLoginSendResult(null);

    public final Throwable failureCause;

    public AudioVideoLoginSendResult(Throwable failureCause) {
        this.failureCause = failureCause;
    }

}
