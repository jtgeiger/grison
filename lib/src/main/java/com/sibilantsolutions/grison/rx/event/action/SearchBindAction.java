package com.sibilantsolutions.grison.rx.event.action;

import io.netty.bootstrap.Bootstrap;

public class SearchBindAction extends AbstractAction {

    public final Bootstrap bootstrap;

    public SearchBindAction(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

}
