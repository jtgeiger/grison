package com.sibilantsolutions.grison.rx.net;

public class ChannelSendEvent {

    public static final ChannelSendEvent IN_FLIGHT = new ChannelSendEvent(null);
    public static final ChannelSendEvent SENT = new ChannelSendEvent(null);

    public final Throwable failureCause;

    private ChannelSendEvent(Throwable failureCause) {
        this.failureCause = failureCause;
    }

    public static ChannelSendEvent fail(Throwable e) {
        return new ChannelSendEvent(e);
    }

    @Override
    public String toString() {
        if (this == IN_FLIGHT) {
            return "ChannelSendEvent{IN_FLIGHT}";
        }
        if (this == SENT) {
            return "ChannelSendEvent{SENT}";
        }
        return "ChannelSendEvent{" +
                "failureCause=" + failureCause +
                '}';
    }
}
