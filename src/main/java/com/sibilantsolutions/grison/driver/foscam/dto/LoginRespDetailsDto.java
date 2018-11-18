package com.sibilantsolutions.grison.driver.foscam.dto;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class LoginRespDetailsDto {

    public static final int CAMERA_ID_LEN = 13;
    public static final int RESERVE1_LEN = 4;
    public static final int RESERVE2_LEN = 4;
    public static final int FIRMWARE_VERSION_LEN = 4;

    public abstract byte[] cameraId();

    public abstract byte[] reserve1();

    public abstract byte[] reserve2();

    public abstract byte[] firmwareVersion();

    public static LoginRespDetailsDto.Builder builder() {
        return new AutoValue_LoginRespDetailsDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder cameraId(byte[] cameraId);

        public abstract Builder reserve1(byte[] reserve1);

        public abstract Builder reserve2(byte[] reserve2);

        public abstract Builder firmwareVersion(byte[] firmwareVersion);

        abstract LoginRespDetailsDto autoBuild();  //Not public

        public LoginRespDetailsDto build() {
            LoginRespDetailsDto dto = autoBuild();

            checkArgument(dto.cameraId().length == CAMERA_ID_LEN, "cameraId len expected=%s actual=%s", CAMERA_ID_LEN, dto.cameraId().length);
            checkArgument(dto.reserve1().length == RESERVE1_LEN, "reserve1 len expected=%s actual=%s", RESERVE1_LEN, dto.reserve1().length);
            checkArgument(dto.reserve2().length == RESERVE2_LEN, "reserve2 len expected=%s actual=%s", RESERVE2_LEN, dto.reserve2().length);
            checkArgument(dto.firmwareVersion().length == FIRMWARE_VERSION_LEN, "firmwareVersion len expected=%s actual=%s", FIRMWARE_VERSION_LEN, dto.firmwareVersion().length);

            return dto;
        }
    }

}
