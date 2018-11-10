package com.sibilantsolutions.grison.rx.event.result;

import io.netty.channel.Channel;

public class AudioVideoConnectResult extends AbstractResult {
    public static final AudioVideoConnectResult IN_FLIGHT = new AudioVideoConnectResult(null, null);

    public final Channel channel;
    public final Throwable failureCause;

    private AudioVideoConnectResult(Channel channel, Throwable failureCause) {
        this.channel = channel;
        this.failureCause = failureCause;
    }

    public static AudioVideoConnectResult success(Channel channel) {
        return new AudioVideoConnectResult(channel, null);
    }

    public static AudioVideoConnectResult fail(Throwable failureCause) {
        return new AudioVideoConnectResult(null, failureCause);
    }

    @Override
    public String toString() {
        if (this == IN_FLIGHT) {
            return "AudioVideoConnectResult{IN_FLIGHT}";
        }
        return "AudioVideoConnectResult{" +
                "channel=" + channel +
                ", failureCause=" + failureCause +
                '}';
    }
}
