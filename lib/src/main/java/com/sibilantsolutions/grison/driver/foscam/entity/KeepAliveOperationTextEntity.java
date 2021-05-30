package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class KeepAliveOperationTextEntity implements FoscamTextEntity {

    public static KeepAliveOperationTextEntity.Builder builder() {
        return new AutoValue_KeepAliveOperationTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract KeepAliveOperationTextEntity build();
    }
}
