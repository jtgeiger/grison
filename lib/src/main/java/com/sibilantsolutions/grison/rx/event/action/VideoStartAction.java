package com.sibilantsolutions.grison.rx.event.action;

import io.netty.channel.Channel;

public class VideoStartAction extends AbstractAction {

    public final Channel channel;

    public VideoStartAction(Channel channel) {
        this.channel = channel;
    }

}
