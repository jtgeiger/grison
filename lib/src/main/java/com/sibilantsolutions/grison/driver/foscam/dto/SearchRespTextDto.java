package com.sibilantsolutions.grison.driver.foscam.dto;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16R;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32R;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

@AutoValue
public abstract class SearchRespTextDto implements FoscamTextDto {

    public static final int CAMERA_ID_LEN = 13;
    public static final int CAMERA_NAME_LEN = 21;
    public static final byte[] RESERVE = {0, 0, 0, 0};
    public static final int SYS_SOFTWARE_VERSION_LEN = 4;
    public static final int APP_SOFTWARE_VERSION_LEN = 4;
    private static final int ENCODED_LEN = CAMERA_ID_LEN + CAMERA_NAME_LEN + 4 + 4 + 4 + 4 + RESERVE.length + SYS_SOFTWARE_VERSION_LEN + APP_SOFTWARE_VERSION_LEN + 2 + 1;

    public abstract byte[] cameraId();

    public abstract byte[] cameraName();

    public abstract FosInt32R ip();

    public abstract FosInt32R mask();

    public abstract FosInt32R gateway();

    public abstract FosInt32R dns();

    public abstract byte[] reserve();

    public abstract byte[] sysSoftwareVersion();

    public abstract byte[] appSoftwareVersion();

    public abstract FosInt16R cameraPort();

    public abstract FosInt8 dhcpEnabled();

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Search_Resp;
    }

    @Override
    public final int encodedLength() {
        return ENCODED_LEN;
    }

    public static Builder builder() {
        return new AutoValue_SearchRespTextDto.Builder()
                .reserve(RESERVE);
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder cameraId(byte[] cameraId);

        public abstract Builder cameraName(byte[] cameraName);

        public abstract Builder ip(FosInt32R ip);

        public abstract Builder mask(FosInt32R mask);

        public abstract Builder gateway(FosInt32R gateway);

        public abstract Builder dns(FosInt32R dns);

        public abstract Builder reserve(byte[] reserve);

        public abstract Builder sysSoftwareVersion(byte[] sysSoftwareVersion);

        public abstract Builder appSoftwareVersion(byte[] appSoftwareVersion);

        public abstract Builder cameraPort(FosInt16R cameraPort);

        public abstract Builder dhcpEnabled(FosInt8 dhcpEnabled);

        abstract SearchRespTextDto autoBuild(); //Not public.

        public SearchRespTextDto build() {
            final SearchRespTextDto dto = autoBuild();

            checkArgument(dto.cameraId().length == CAMERA_ID_LEN, "cameraId len expected=%s actual=%s", dto.cameraId().length, CAMERA_ID_LEN);
            checkArgument(dto.cameraName().length == CAMERA_NAME_LEN, "cameraName len expected=%s actual=%s", dto.cameraName().length, CAMERA_NAME_LEN);
            checkArgument(dto.reserve().length == RESERVE.length, "reserve len expected=%s actual=%s", dto.reserve().length, RESERVE.length);
            checkArgument(dto.sysSoftwareVersion().length == SYS_SOFTWARE_VERSION_LEN, "sysSoftwareVersion len expected=%s actual=%s", dto.sysSoftwareVersion().length, SYS_SOFTWARE_VERSION_LEN);
            checkArgument(dto.appSoftwareVersion().length == APP_SOFTWARE_VERSION_LEN, "appSoftwareVersion len expected=%s actual=%s", dto.appSoftwareVersion().length, APP_SOFTWARE_VERSION_LEN);

            return dto;
        }
    }
}
