package com.sibilantsolutions.grison.rx.event.action;

import io.netty.channel.Channel;

public class VerifyAction extends AbstractAction {

    public final Channel channel;
    public final String username;
    public final String password;

    public VerifyAction(Channel channel, String username, String password) {
        this.channel = channel;
        this.username = username;
        this.password = password;
    }
}
