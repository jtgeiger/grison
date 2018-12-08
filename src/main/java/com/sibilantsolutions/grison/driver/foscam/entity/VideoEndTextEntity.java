package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VideoEndTextEntity implements FoscamTextEntity {

    public static VideoEndTextEntity.Builder builder() {
        return new AutoValue_VideoEndTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract VideoEndTextEntity build();
    }
}
