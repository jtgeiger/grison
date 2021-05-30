package com.sibilantsolutions.grison.rx.event.result;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.entity.FoscamTextEntity;

@AutoValue
public abstract class AudioVideoReceiveResult extends AbstractResult {

    public abstract FoscamTextEntity text();

    public static AudioVideoReceiveResult create(FoscamTextEntity text) {
        return new AutoValue_AudioVideoReceiveResult(text);
    }
}
