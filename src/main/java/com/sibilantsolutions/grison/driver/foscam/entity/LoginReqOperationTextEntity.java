package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class LoginReqOperationTextEntity implements FoscamTextEntity {

    public static LoginReqOperationTextEntity.Builder builder() {
        return new AutoValue_LoginReqOperationTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract LoginReqOperationTextEntity build();
    }
}
