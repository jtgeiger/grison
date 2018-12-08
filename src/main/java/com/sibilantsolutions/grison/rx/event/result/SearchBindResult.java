package com.sibilantsolutions.grison.rx.event.result;

import io.netty.channel.Channel;

public class SearchBindResult extends AbstractResult {
    public static final SearchBindResult IN_FLIGHT = new SearchBindResult(null, null);

    public final Channel channel;
    public final Throwable failureCause;

    private SearchBindResult(Channel channel, Throwable failureCause) {
        this.channel = channel;
        this.failureCause = failureCause;
    }

    public static SearchBindResult success(Channel channel) {
        return new SearchBindResult(channel, null);
    }

    public static SearchBindResult fail(Throwable failureCause) {
        return new SearchBindResult(null, failureCause);
    }

    @Override
    public String toString() {
        if (this == IN_FLIGHT) {
            return "SearchBindResult{IN_FLIGHT}";
        }
        return "SearchBindResult{" +
                "channel=" + channel +
                ", failureCause=" + failureCause +
                '}';
    }
}
