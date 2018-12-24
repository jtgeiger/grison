package com.sibilantsolutions.grison.rx.net;

import java.net.SocketAddress;

public class ConnectionRequestEvent {

    public final SocketAddress socketAddress;

    public ConnectionRequestEvent(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    @Override
    public String toString() {
        return "ConnectionRequestEvent{" +
                "socketAddress=" + socketAddress +
                '}';
    }
}
