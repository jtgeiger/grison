package com.sibilantsolutions.grison.rx.event.action;

import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import io.netty.channel.Channel;

public class AudioVideoLoginAction extends AbstractAction {

    public final Channel channel;
    public final FosInt32 dataConnectionId;

    public AudioVideoLoginAction(Channel channel, FosInt32 dataConnectionId) {
        this.channel = channel;
        this.dataConnectionId = dataConnectionId;
    }
}
