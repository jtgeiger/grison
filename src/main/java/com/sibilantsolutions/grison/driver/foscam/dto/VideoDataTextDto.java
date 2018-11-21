package com.sibilantsolutions.grison.driver.foscam.dto;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

@AutoValue
public abstract class VideoDataTextDto implements FoscamTextDto {

    public abstract FosInt32 timestamp();

    public abstract FosInt32 framePerSec();

    public abstract FosInt8 reserve();

    public abstract FosInt32 videoLength();

    public abstract byte[] videoData();

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Video_Data;
    }

    @Override
    public final int encodedLength() {
        return 4 + 4 + 1 + 4 + videoData().length;
    }

    public static Builder builder() {
        return new AutoValue_VideoDataTextDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder timestamp(FosInt32 timestamp);

        public abstract Builder framePerSec(FosInt32 framePerSec);

        public abstract Builder reserve(FosInt8 reserve);

        public abstract Builder videoLength(FosInt32 videoLength);

        public abstract Builder videoData(byte[] videoData);

        abstract VideoDataTextDto autoBuild();  //Not public.

        public VideoDataTextDto build() {
            final VideoDataTextDto dto = autoBuild();

            checkArgument(dto.videoLength().value() == dto.videoData().length, "video data len expected=%s actual=%s", dto.videoLength().value(), dto.videoData().length);

            return dto;
        }
    }
}
