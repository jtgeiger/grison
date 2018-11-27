package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VerifyReqTextEntity implements FoscamTextEntity {

    public abstract String username();

    public abstract String password();

    public static VerifyReqTextEntity.Builder builder() {
        return new AutoValue_VerifyReqTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder username(String username);

        public abstract Builder password(String password);

        public abstract VerifyReqTextEntity build();
    }
}
