package com.sibilantsolutions.grison.rx.event.result;

public class VideoEndSendResult extends AbstractResult {

    public static final VideoEndSendResult IN_FLIGHT = new VideoEndSendResult(null);

    public final Throwable failureCause;

    public VideoEndSendResult(Throwable failureCause) {
        this.failureCause = failureCause;
    }

}
