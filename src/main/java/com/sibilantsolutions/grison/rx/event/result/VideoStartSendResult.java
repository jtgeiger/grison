package com.sibilantsolutions.grison.rx.event.result;

public class VideoStartSendResult extends AbstractResult {

    public static final VideoStartSendResult IN_FLIGHT = new VideoStartSendResult(null);

    public final Throwable failureCause;

    public VideoStartSendResult(Throwable failureCause) {
        this.failureCause = failureCause;
    }

}
