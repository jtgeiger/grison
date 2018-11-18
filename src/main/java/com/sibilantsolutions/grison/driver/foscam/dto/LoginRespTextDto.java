package com.sibilantsolutions.grison.driver.foscam.dto;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;

@AutoValue
public abstract class LoginRespTextDto implements FoscamTextDto {

    public static final int CAMERA_ID_LEN = 13;
    public static final int RESERVE1_LEN = 4;
    public static final int RESERVE2_LEN = 4;
    public static final int FIRMWARE_VERSION_LEN = 4;

    public abstract FosInt16 resultCode();

    public abstract Optional<byte[]> cameraId();

    public abstract Optional<byte[]> reserve1();

    public abstract Optional<byte[]> reserve2();

    public abstract Optional<byte[]> firmwareVersion();

    public static Builder builder() {
        return new AutoValue_LoginRespTextDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder resultCode(FosInt16 resultCode);

        public abstract Builder cameraId(byte[] cameraId);

        public abstract Builder reserve1(byte[] reserve1);

        public abstract Builder reserve2(byte[] reserve2);

        public abstract Builder firmwareVersion(byte[] firmwareVersion);

        abstract LoginRespTextDto autoBuild();  //Not public

        public LoginRespTextDto build() {
            LoginRespTextDto dto = autoBuild();
            if (ResultCodeE.fromValue(dto.resultCode().value()) == ResultCodeE.CORRECT) {
                checkArgument(dto.cameraId().get().length == CAMERA_ID_LEN, "cameraId len expected=%s actual=%s", CAMERA_ID_LEN, dto.cameraId().get().length);
                checkArgument(dto.reserve1().get().length == RESERVE1_LEN, "reserve1 len expected=%s actual=%s", RESERVE1_LEN, dto.reserve1().get().length);
                checkArgument(dto.reserve2().get().length == RESERVE2_LEN, "reserve2 len expected=%s actual=%s", RESERVE2_LEN, dto.reserve2().get().length);
                checkArgument(dto.firmwareVersion().get().length == FIRMWARE_VERSION_LEN, "firmwareVersion len expected=%s actual=%s", FIRMWARE_VERSION_LEN, dto.firmwareVersion().get().length);
            } else {
                checkArgument(!dto.cameraId().isPresent(), "expected cameraId to be absent");
                checkArgument(!dto.reserve1().isPresent(), "expected reserve1 to be absent");
                checkArgument(!dto.reserve2().isPresent(), "expected reserve2 to be absent");
                checkArgument(!dto.firmwareVersion().isPresent(), "expected firmwareVersion to be absent");
            }

            return dto;
        }
    }

}
