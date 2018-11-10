package com.sibilantsolutions.grison.rx.event.action;

import io.netty.channel.Channel;

public class VideoEndAction extends AbstractAction {

    public final Channel channel;

    public VideoEndAction(Channel channel) {
        this.channel = channel;
    }

}
