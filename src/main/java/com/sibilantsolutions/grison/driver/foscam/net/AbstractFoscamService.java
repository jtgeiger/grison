package com.sibilantsolutions.grison.driver.foscam.net;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;

public abstract class AbstractFoscamService {

    private FoscamConnection connection;

    public AbstractFoscamService(FoscamConnection connection) {
        this.connection = connection;
    }

    void sendAsync(Command request) {
        connection.sendAsync(request);
    }

    Command sendReceive(Command request) {
        return connection.sendReceive(request);
    }

}
