package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TalkStartReqTextEntity implements FoscamTextEntity {

    public static TalkStartReqTextEntity.Builder builder() {
        return new AutoValue_TalkStartReqTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract TalkStartReqTextEntity build();
    }
}
