package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AudioStartReqTextEntity implements FoscamTextEntity {

    public static AudioStartReqTextEntity.Builder builder() {
        return new AutoValue_AudioStartReqTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract AudioStartReqTextEntity build();
    }
}
