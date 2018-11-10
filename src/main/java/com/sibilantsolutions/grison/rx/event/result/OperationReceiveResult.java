package com.sibilantsolutions.grison.rx.event.result;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;

public class OperationReceiveResult extends AbstractResult {

    public final Command command;

    public OperationReceiveResult(Command command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "OperationReceiveResult{" +
                "command=" + command +
                '}';
    }

}
