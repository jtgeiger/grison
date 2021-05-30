package com.sibilantsolutions.grison.rx.event.result;

public class VerifySendResult extends AbstractResult {

    public static final VerifySendResult IN_FLIGHT = new VerifySendResult(null);
    public static final VerifySendResult SENT = new VerifySendResult(null);

    public final Throwable failureCause;

    public VerifySendResult(Throwable failureCause) {
        this.failureCause = failureCause;
    }

}
