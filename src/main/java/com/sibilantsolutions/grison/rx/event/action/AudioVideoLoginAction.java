package com.sibilantsolutions.grison.rx.event.action;

import io.netty.channel.Channel;

public class AudioVideoLoginAction extends AbstractAction {

    public final Channel channel;
    public final byte[] dataConnectionId;

    public AudioVideoLoginAction(Channel channel, byte[] dataConnectionId) {
        this.channel = channel;
        this.dataConnectionId = dataConnectionId;
    }
}
