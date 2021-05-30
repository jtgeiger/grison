package com.sibilantsolutions.grison.driver.foscam.entity;

import java.time.Duration;
import java.time.Instant;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.domain.AudioFormatE;

@AutoValue
public abstract class AudioDataTextEntity implements FoscamTextEntity {

    public abstract Duration uptime();

    public abstract long serialNumber();

    public abstract Instant timestamp();

    public abstract AudioFormatE audioFormat();

    public abstract byte[] data();

    public static AudioDataTextEntity.Builder builder() {
        return new AutoValue_AudioDataTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder uptime(Duration uptime);

        public abstract Builder serialNumber(long serialNumber);

        public abstract Builder timestamp(Instant timestamp);

        public abstract Builder audioFormat(AudioFormatE audioFormat);

        public abstract Builder data(byte[] data);

        public abstract AudioDataTextEntity build();
    }
}
