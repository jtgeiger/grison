package com.sibilantsolutions.grison.rx.event.action;

public class OperationConnectAction extends AbstractAction {
    public final String host;
    public final int port;

    public OperationConnectAction(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
