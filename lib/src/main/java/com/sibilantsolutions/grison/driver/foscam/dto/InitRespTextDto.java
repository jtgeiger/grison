package com.sibilantsolutions.grison.driver.foscam.dto;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;

@AutoValue
public abstract class InitRespTextDto implements FoscamTextDto {

    public abstract FosInt16 resultCode();

    public static Builder builder() {
        return new AutoValue_InitRespTextDto.Builder();
    }

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Init_Resp;
    }

    @Override
    public final int encodedLength() {
        return 2;
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder resultCode(FosInt16 resultCode);

        public abstract InitRespTextDto build();

    }

}
