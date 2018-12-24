package com.sibilantsolutions.grison.rx.net;

import io.netty.channel.Channel;

public class ChannelConnectEvent {

    static public final ChannelConnectEvent IN_FLIGHT = new ChannelConnectEvent(null, null);

    public final Channel channel;
    public final Throwable failureCause;

    private ChannelConnectEvent(Channel channel, Throwable failureCause) {
        this.channel = channel;
        this.failureCause = failureCause;
    }

    public static ChannelConnectEvent success(Channel channel) {
        return new ChannelConnectEvent(channel, null);
    }

    public static ChannelConnectEvent fail(Throwable e) {
        return new ChannelConnectEvent(null, e);
    }

    @Override
    public String toString() {
        if (this == IN_FLIGHT) {
            return "ChannelConnectEvent{IN_FLIGHT}";
        }
        return "ChannelConnectEvent{" +
                "channel=" + channel +
                ", failureCause=" + failureCause +
                '}';
    }
}
