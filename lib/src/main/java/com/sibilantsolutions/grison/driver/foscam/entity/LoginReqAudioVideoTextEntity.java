package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;

@AutoValue
public abstract class LoginReqAudioVideoTextEntity implements FoscamTextEntity {

    public abstract FosInt32 dataConnectionId();

    public static LoginReqAudioVideoTextEntity.Builder builder() {
        return new AutoValue_LoginReqAudioVideoTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder dataConnectionId(FosInt32 dataConnectionId);

        public abstract LoginReqAudioVideoTextEntity build();
    }
}
