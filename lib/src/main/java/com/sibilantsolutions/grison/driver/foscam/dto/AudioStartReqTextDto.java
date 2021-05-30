package com.sibilantsolutions.grison.driver.foscam.dto;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

@AutoValue
public abstract class AudioStartReqTextDto implements FoscamTextDto {

    public abstract FosInt8 data();

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Audio_Start_Req;
    }

    @Override
    public final int encodedLength() {
        return 1;
    }

    public static Builder builder() {
        return new AutoValue_AudioStartReqTextDto.Builder()
                .data(FosInt8.create((byte) 2));
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder data(FosInt8 data);

        public abstract AudioStartReqTextDto build();
    }
}
