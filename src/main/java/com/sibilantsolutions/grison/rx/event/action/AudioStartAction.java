package com.sibilantsolutions.grison.rx.event.action;

import io.netty.channel.Channel;

public class AudioStartAction extends AbstractAction {

    public final Channel channel;

    public AudioStartAction(Channel channel) {
        this.channel = channel;
    }

}
