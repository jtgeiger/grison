package com.sibilantsolutions.grison.rx.event.action;

import io.netty.channel.Channel;

public class AudioEndAction extends AbstractAction {

    public final Channel channel;

    public AudioEndAction(Channel channel) {
        this.channel = channel;
    }

}
