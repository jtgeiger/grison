package com.sibilantsolutions.grison.rx.event.ui;

public class ConnectUiEvent extends UiEvent {
    public final String host;
    public final int port;

    public ConnectUiEvent(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
