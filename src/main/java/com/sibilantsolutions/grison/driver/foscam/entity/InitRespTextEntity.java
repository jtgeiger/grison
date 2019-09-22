package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;

@AutoValue
public abstract class InitRespTextEntity implements FoscamTextEntity {

    public abstract ResultCodeE result();

    public static InitRespTextEntity.Builder builder() {
        return new AutoValue_InitRespTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder result(ResultCodeE result);

        public abstract InitRespTextEntity build();
    }
}
