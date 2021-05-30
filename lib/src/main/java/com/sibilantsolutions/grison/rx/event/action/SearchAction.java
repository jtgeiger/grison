package com.sibilantsolutions.grison.rx.event.action;

import io.netty.channel.Channel;

public class SearchAction extends AbstractAction {

    public final Channel channel;

    public SearchAction(Channel channel) {
        this.channel = channel;
    }

}
