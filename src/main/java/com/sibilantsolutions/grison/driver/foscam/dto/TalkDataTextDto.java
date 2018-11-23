package com.sibilantsolutions.grison.driver.foscam.dto;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

@AutoValue
public abstract class TalkDataTextDto implements FoscamTextDto {

    public abstract FosInt32 timestampMs();

    public abstract FosInt32 snOfPacket();

    public abstract FosInt32 gatherTimeSecs();

    public abstract FosInt8 audioFormat();

    public abstract FosInt32 dataLength();

    public abstract byte[] data();

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Talk_Data;
    }

    @Override
    public final int encodedLength() {
        return 4 + 4 + 4 + 1 + 4 + data().length;
    }

    public static Builder builder() {
        return new AutoValue_TalkDataTextDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder timestampMs(FosInt32 timestampMs);

        public abstract Builder snOfPacket(FosInt32 snOfPacket);

        public abstract Builder gatherTimeSecs(FosInt32 gatherTimeSecs);

        public abstract Builder audioFormat(FosInt8 audioFormat);

        public abstract Builder dataLength(FosInt32 dataLength);

        public abstract Builder data(byte[] data);

        abstract TalkDataTextDto autoBuild();  //Not public.

        public TalkDataTextDto build() {
            final TalkDataTextDto dto = autoBuild();

            checkArgument(dto.dataLength().value() == dto.data().length, "data len expected=%s actual=%s", dto.dataLength(), dto.data().length);

            return dto;
        }
    }
}
