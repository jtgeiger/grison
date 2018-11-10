package com.sibilantsolutions.grison.rx.event.action;

import io.netty.channel.Channel;

public class LoginAction extends AbstractAction {

    public final Channel channel;

    public LoginAction(Channel channel) {
        this.channel = channel;
    }

}
