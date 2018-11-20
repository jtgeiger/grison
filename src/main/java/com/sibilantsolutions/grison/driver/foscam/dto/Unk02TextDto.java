package com.sibilantsolutions.grison.driver.foscam.dto;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Unk02TextDto implements FoscamTextDto {

    public static final int DATA_LEN = 1152;

    public abstract byte[] data();

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.UNK02;
    }

    @Override
    public final int encodedLength() {
        return DATA_LEN;
    }

    public static Builder builder() {
        return new AutoValue_Unk02TextDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder data(byte[] data);

        abstract Unk02TextDto autoBuild();  //Not public

        public Unk02TextDto build() {
            final Unk02TextDto dto = autoBuild();

            checkArgument(dto.data().length == DATA_LEN, "data len expected=%s actual=%s", DATA_LEN, dto.data().length);

            return dto;
        }
    }
}
