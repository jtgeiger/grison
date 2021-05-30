package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VideoStartReqTextEntity implements FoscamTextEntity {

    public static VideoStartReqTextEntity.Builder builder() {
        return new AutoValue_VideoStartReqTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract VideoStartReqTextEntity build();
    }
}
