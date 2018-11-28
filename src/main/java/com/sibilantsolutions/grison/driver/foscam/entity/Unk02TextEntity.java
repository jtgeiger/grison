package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Unk02TextEntity implements FoscamTextEntity {

    public static Unk02TextEntity.Builder builder() {
        return new AutoValue_Unk02TextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Unk02TextEntity build();
    }
}
