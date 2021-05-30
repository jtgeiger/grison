package com.sibilantsolutions.grison.driver.foscam.dto;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

@AutoValue
public abstract class VerifyRespTextDto implements FoscamTextDto {

    public abstract FosInt16 resultCode();

    public abstract Optional<FosInt8> reserve();

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Verify_Resp;
    }

    @Override
    public final int encodedLength() {
        return 2 + reserve().map(fosInt8 -> 1).orElse(0);
    }

    public static Builder builder() {
        return new AutoValue_VerifyRespTextDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder resultCode(FosInt16 resultCode);

        public abstract Builder reserve(FosInt8 reserve);

        abstract VerifyRespTextDto autoBuild(); //Not public.

        public VerifyRespTextDto build() {
            final VerifyRespTextDto dto = autoBuild();

            if (ResultCodeE.fromValue(dto.resultCode()) == ResultCodeE.CORRECT) {
                checkArgument(dto.reserve().isPresent(), "reserve missing");
            } else {
                checkArgument(!dto.reserve().isPresent(), "expected reserve to be absent");
            }

            return dto;
        }
    }
}
