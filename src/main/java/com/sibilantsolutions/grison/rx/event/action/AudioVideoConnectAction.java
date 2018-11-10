package com.sibilantsolutions.grison.rx.event.action;

public class AudioVideoConnectAction extends AbstractAction {
    public final String host;
    public final int port;

    public AudioVideoConnectAction(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
