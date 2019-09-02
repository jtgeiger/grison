package com.sibilantsolutions.grison.driver.foscam.dto;

import static com.google.common.base.Preconditions.checkArgument;

import java.time.Instant;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

@AutoValue
public abstract class CommandDto {

    public static final int PROTOCOL_LEN = 4;
    public static final FosInt8 RESERVE1 = FosInt8.ZERO;
    public static final int RESERVE2_LEN = 8;
    public static final byte[] RESERVE2 = new byte[RESERVE2_LEN];
    /**
     * protocol(4) + opcode(2) + reserve1(1) + reserve2(8) + textLength(4) + reserve3(4) = 23
     */
    public static final int COMMAND_PREFIX_LENGTH = PROTOCOL_LEN + 2 + 1 + RESERVE2_LEN + 4 + 4;

    public abstract ProtocolE protocol();

    public abstract FoscamOpCode operationCode();

    public abstract FosInt8 reserve1();

    public abstract byte[] reserve2();

    public abstract FosInt32 textLength();

    public abstract FosInt32 reserve3();

    public abstract FoscamTextDto text();

    public abstract Instant clientTimestamp();

    public static Builder builder() {
        return new AutoValue_CommandDto.Builder()
                .reserve1(RESERVE1)
                .reserve2(RESERVE2);
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

        public abstract Builder clientTimestamp(Instant clientTimestamp);

        abstract CommandDto autoBuild();    //Not public.

        public CommandDto build() {
            CommandDto dto = autoBuild();
            checkArgument(dto.reserve2().length == RESERVE2.length, "reserve2 len expected=%s actual=%s", RESERVE2.length, dto.reserve2().length);
            checkArgument(dto.operationCode().equals(dto.text().opCode()), "opcode expected=%s actual=%s", dto.operationCode(), dto.text().opCode());
            return dto;
        }
    }
}
