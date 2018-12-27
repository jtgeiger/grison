package com.sibilantsolutions.grison.driver.foscam.dto;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;

@AutoValue
public abstract class AudioStartRespTextDto implements FoscamTextDto {

    public abstract FosInt16 result();

    public abstract Optional<FosInt32> dataConnectionId();

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Audio_Start_Resp;
    }

    @Override
    public final int encodedLength() {
        return 2 + dataConnectionId().map(dataConnectionId -> 4).orElse(0);
    }

    public static Builder builder() {
        return new AutoValue_AudioStartRespTextDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder result(FosInt16 result);

        public abstract Builder dataConnectionId(FosInt32 dataConnectionId);

        abstract AudioStartRespTextDto autoBuild();  //Not public.

        public AudioStartRespTextDto build() {
            final AudioStartRespTextDto dto = autoBuild();

            //If ResultCodeE.CORRECT the data connection id may be present, or not if it has already been sent in this
            // session.  If it's not ResultCodeE.CORRECT then it should never be present.
            if (ResultCodeE.fromValue(dto.result()) != ResultCodeE.CORRECT) {
                checkArgument(!dto.dataConnectionId().isPresent(), "expected dataConnectionId to be absent");
            }

            return dto;
        }
    }
}
