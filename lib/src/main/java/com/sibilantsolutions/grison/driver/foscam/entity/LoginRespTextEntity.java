package com.sibilantsolutions.grison.driver.foscam.entity;

import java.util.Optional;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;

@AutoValue
public abstract class LoginRespTextEntity implements FoscamTextEntity {

    public abstract ResultCodeE resultCode();

    public abstract Optional<String> cameraId();

    public abstract Optional<VersionEntity> version();

    public static Builder builder() {
        return new AutoValue_LoginRespTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder resultCode(ResultCodeE resultCode);

        public abstract Builder cameraId(String cameraId);

        public abstract Builder version(VersionEntity version);

        public abstract LoginRespTextEntity build();
    }

}
