package com.sibilantsolutions.grison.rx.event.result;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;

public class AudioVideoReceiveResult extends AbstractResult {

    public final CommandDto command;

    public AudioVideoReceiveResult(CommandDto command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "AudioVideoReceiveResult{" +
                "command=" + command +
                '}';
    }

}
