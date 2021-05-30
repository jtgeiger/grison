package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;

@AutoValue
public abstract class VerifyRespTextEntity implements FoscamTextEntity {

    public abstract ResultCodeE resultCode();

    public static VerifyRespTextEntity.Builder builder() {
        return new AutoValue_VerifyRespTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder resultCode(ResultCodeE resultCode);

        public abstract VerifyRespTextEntity build();
    }
}
