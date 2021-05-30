package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class KeepAliveAudioVideoTextEntity implements FoscamTextEntity {

    public static KeepAliveAudioVideoTextEntity.Builder builder() {
        return new AutoValue_KeepAliveAudioVideoTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract KeepAliveAudioVideoTextEntity build();
    }
}
