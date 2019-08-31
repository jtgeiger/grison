package com.sibilantsolutions.grison.driver.foscam.dto;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16R;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32R;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

@AutoValue
public abstract class InitReqTextDto implements FoscamTextDto {

    public static final FosInt8 RESERVE1 = FosInt8.ZERO;
    public static final FosInt8 RESERVE2 = FosInt8.ZERO;
    public static final FosInt8 RESERVE3 = FosInt8.ZERO;
    public static final FosInt8 RESERVE4 = FosInt8.ONE;

    public static final int CAMERA_ID_LEN = 13;
    public static final int USER_LEN = 13;
    public static final int PASSWORD_LEN = 13;

    public abstract FosInt8 reserve1();

    public abstract FosInt8 reserve2();

    public abstract FosInt8 reserve3();

    public abstract FosInt8 reserve4();

    public abstract byte[] cameraId();

    public abstract byte[] user();

    public abstract byte[] password();

    public abstract FosInt32R ip();

    public abstract FosInt32R mask();

    public abstract FosInt32R gateway();

    public abstract FosInt32R dns();

    public abstract FosInt16R cameraPort();

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Init_Req;
    }

    @Override
    public final int encodedLength() {
        return 1 + 1 + 1 + 1 + CAMERA_ID_LEN + USER_LEN + PASSWORD_LEN + 4 + 4 + 4 + 4 + 2;
    }

    public static Builder builder() {
        return new AutoValue_InitReqTextDto.Builder()
                .reserve1(RESERVE1)
                .reserve2(RESERVE2)
                .reserve3(RESERVE3)
                .reserve4(RESERVE4);
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder reserve1(FosInt8 reserve1);

        public abstract Builder reserve2(FosInt8 reserve2);

        public abstract Builder reserve3(FosInt8 reserve3);

        public abstract Builder reserve4(FosInt8 reserve4);

        public abstract Builder cameraId(byte[] cameraId);

        public abstract Builder user(byte[] user);

        public abstract Builder password(byte[] password);

        public abstract Builder ip(FosInt32R ip);

        public abstract Builder mask(FosInt32R mask);

        public abstract Builder gateway(FosInt32R gateway);

        public abstract Builder dns(FosInt32R dns);

        public abstract Builder cameraPort(FosInt16R cameraPort);

        abstract InitReqTextDto autoBuild(); //Not public.

        public InitReqTextDto build() {
            final InitReqTextDto dto = autoBuild();

            checkArgument(dto.cameraId().length == CAMERA_ID_LEN, "cameraId len expected=%s actual=%s", dto.cameraId().length, CAMERA_ID_LEN);
            checkArgument(dto.user().length == USER_LEN, "user len expected=%s actual=%s", dto.user().length, USER_LEN);
            checkArgument(dto.password().length == PASSWORD_LEN, "password len expected=%s actual=%s", dto.password().length, PASSWORD_LEN);

            return dto;
        }
    }
}
