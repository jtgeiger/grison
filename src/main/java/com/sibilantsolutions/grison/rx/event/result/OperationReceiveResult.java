package com.sibilantsolutions.grison.rx.event.result;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;

public class OperationReceiveResult extends AbstractResult {

    public final CommandDto command;

    public OperationReceiveResult(CommandDto command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "OperationReceiveResult{" +
                "command=" + command +
                '}';
    }

}
