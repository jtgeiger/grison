package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VersionEntity {

    public abstract int major();

    public abstract int minor();

    public abstract int patch();

    public abstract int buildNum();

    public static Builder builder() {
        return new AutoValue_VersionEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder major(int major);

        public abstract Builder minor(int minor);

        public abstract Builder patch(int patch);

        public abstract Builder buildNum(int buildNum);

        public abstract VersionEntity build();

    }

}
