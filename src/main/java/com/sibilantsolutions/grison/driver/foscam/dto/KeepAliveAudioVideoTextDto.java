package com.sibilantsolutions.grison.driver.foscam.dto;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class KeepAliveAudioVideoTextDto implements FoscamTextDto {

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Keep_Alive_AudioVideo;
    }

    @Override
    public final int encodedLength() {
        return 0;
    }

    public static Builder builder() {
        return new AutoValue_KeepAliveAudioVideoTextDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract KeepAliveAudioVideoTextDto build();
    }
}
