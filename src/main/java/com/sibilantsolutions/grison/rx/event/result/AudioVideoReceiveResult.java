package com.sibilantsolutions.grison.rx.event.result;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;

public class AudioVideoReceiveResult extends AbstractResult {

    public final Command command;

    public AudioVideoReceiveResult(Command command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "AudioVideoReceiveResult{" +
                "command=" + command +
                '}';
    }

}
