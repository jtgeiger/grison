package com.sibilantsolutions.grison.driver.foscam.dto;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

@AutoValue
public abstract class AudioStartReqTextDto implements FoscamTextDto {

    public static final FosInt8 RESERVE = FosInt8.create(1);

    public abstract FosInt8 reserve();

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
                .reserve(RESERVE);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder reserve(FosInt8 reserve);

        public abstract AudioStartReqTextDto build();
    }
}
