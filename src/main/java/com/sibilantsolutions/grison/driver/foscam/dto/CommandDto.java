package com.sibilantsolutions.grison.driver.foscam.dto;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

@AutoValue
public abstract class CommandDto {

    public static final int PROTOCOL_LEN = 4;
    public static final int RESERVE2_LEN = 8;

    public static final int COMMAND_PREFIX_LENGTH = 4 + 2 + 1 + 8 + 4 + 4;

    public static final FosInt8 RESERVE1 = FosInt8.create(0);
    public static final byte[] RESERVE2 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};

    public abstract ProtocolE protocol();

    public abstract FoscamOpCode operationCode();

    public abstract FosInt8 reserve1();

    public abstract byte[] reserve2();

    public abstract FosInt32 textLength();

    public abstract FosInt32 reserve3();

    public abstract FoscamTextDto text();

    public static Builder builder() {
        return new AutoValue_CommandDto.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder protocol(ProtocolE protocol);

        public abstract Builder operationCode(FoscamOpCode operationCode);

        public abstract Builder reserve1(FosInt8 reserve1);

        public abstract Builder reserve2(byte[] reserve2);

        public abstract Builder textLength(FosInt32 textLength);

        public abstract Builder reserve3(FosInt32 reserve3);

        public abstract Builder text(FoscamTextDto text);

        abstract CommandDto autoBuild();    //Not public.

        public CommandDto build() {
            CommandDto dto = autoBuild();
            checkArgument(dto.reserve2().length == RESERVE2_LEN, "reserve2 len expected=%s actual=%s", RESERVE2_LEN, dto.reserve2().length);
            return dto;
        }
    }
}
