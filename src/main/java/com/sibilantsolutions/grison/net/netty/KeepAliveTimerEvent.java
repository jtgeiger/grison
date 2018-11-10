package com.sibilantsolutions.grison.net.netty;

import java.util.concurrent.TimeUnit;

public class KeepAliveTimerEvent {
    private final int period;
    private final TimeUnit timeUnit;

    public KeepAliveTimerEvent(int period, TimeUnit timeUnit) {
        this.period = period;
        this.timeUnit = timeUnit;
    }

    @Override
    public String toString() {
        return "KeepAliveTimerEvent{" +
                "period=" + period +
                ", timeUnit=" + timeUnit +
                '}';
    }

}
