package com.sibilantsolutions.grison.rx.event.result;

import io.netty.channel.Channel;

public class OperationConnectResult extends AbstractResult {
    public static final OperationConnectResult IN_FLIGHT = new OperationConnectResult(null, null);

    public final Channel channel;
    public final Throwable failureCause;

    private OperationConnectResult(Channel channel, Throwable failureCause) {
        this.channel = channel;
        this.failureCause = failureCause;
    }

    public static OperationConnectResult success(Channel channel) {
        return new OperationConnectResult(channel, null);
    }

    public static OperationConnectResult fail(Throwable failureCause) {
        return new OperationConnectResult(null, failureCause);
    }

    @Override
    public String toString() {
        if (this == IN_FLIGHT) {
            return "OperationConnectResult{IN_FLIGHT}";
        }
        return "OperationConnectResult{" +
                "channel=" + channel +
                ", failureCause=" + failureCause +
                '}';
    }
}
