package com.sibilantsolutions.grison.net.retrofit;

import java.time.Instant;

import com.google.auto.value.AutoValue;

@AutoValue
abstract public class SetTimeDto {

    public abstract long unixEpochSeconds();

    public static SetTimeDto create(Instant instant) {
        return new AutoValue_SetTimeDto(instant.getEpochSecond());
    }

}
