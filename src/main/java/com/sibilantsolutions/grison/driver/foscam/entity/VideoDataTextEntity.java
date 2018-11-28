package com.sibilantsolutions.grison.driver.foscam.entity;

import java.time.Instant;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VideoDataTextEntity implements FoscamTextEntity {

    public abstract long uptimeMs();

    public abstract Instant timestamp();

    public abstract byte[] videoData();

    public static VideoDataTextEntity.Builder builder() {
        return new AutoValue_VideoDataTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder uptimeMs(long uptimeMs);

        public abstract Builder timestamp(Instant timestamp);

        public abstract Builder videoData(byte[] videoData);

        public abstract VideoDataTextEntity build();
    }
}
