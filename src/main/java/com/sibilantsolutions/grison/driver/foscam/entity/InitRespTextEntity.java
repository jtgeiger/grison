package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class InitRespTextEntity implements FoscamTextEntity {

    public static InitRespTextEntity.Builder builder() {
        return new AutoValue_InitRespTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract InitRespTextEntity build();
    }
}
