package com.sibilantsolutions.grison.net.netty;

import java.time.Duration;

public class KeepAliveTimerEvent {
    private final Duration keepaliveSendTimeout;

    public KeepAliveTimerEvent(Duration keepaliveSendTimeout) {
        this.keepaliveSendTimeout = keepaliveSendTimeout;
    }

    @Override
    public String toString() {
        return "KeepAliveTimerEvent{" +
                "keepaliveSendTimeout=" + keepaliveSendTimeout +
                '}';
    }
}
