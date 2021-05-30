package com.sibilantsolutions.grison.driver.foscam.dto;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TalkEndTextDto implements FoscamTextDto {

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Talk_End;
    }

    @Override
    public final int encodedLength() {
        return 0;
    }

    public static Builder builder() {
        return new AutoValue_TalkEndTextDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract TalkEndTextDto build();
    }
}
