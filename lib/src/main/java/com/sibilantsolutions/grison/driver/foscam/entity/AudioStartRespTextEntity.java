package com.sibilantsolutions.grison.driver.foscam.entity;

import java.util.Optional;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;

@AutoValue
public abstract class AudioStartRespTextEntity implements FoscamTextEntity {

    public abstract ResultCodeE result();

    public abstract Optional<FosInt32> dataConnectionId();

    public static AudioStartRespTextEntity.Builder builder() {
        return new AutoValue_AudioStartRespTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder result(ResultCodeE result);

        public abstract Builder dataConnectionId(FosInt32 dataConnectionId);

        public abstract AudioStartRespTextEntity build();
    }
}
