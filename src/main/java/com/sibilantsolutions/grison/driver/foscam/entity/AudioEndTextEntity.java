package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AudioEndTextEntity implements FoscamTextEntity {

    public static AudioEndTextEntity.Builder builder() {
        return new AutoValue_AudioEndTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract AudioEndTextEntity build();
    }
}
