package com.sibilantsolutions.grison.rx.net;

import java.net.SocketAddress;

import com.google.auto.value.AutoValue;
import io.netty.channel.ChannelHandler;

@AutoValue
public abstract class ConnectionRequestEvent {

    public abstract SocketAddress socketAddress();

    public abstract ChannelHandler channelHandler();

    public static ConnectionRequestEvent create(SocketAddress socketAddress, ChannelHandler channelHandler) {
        return new AutoValue_ConnectionRequestEvent(socketAddress, channelHandler);
    }

}
