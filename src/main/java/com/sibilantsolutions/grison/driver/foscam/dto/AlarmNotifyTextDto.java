package com.sibilantsolutions.grison.driver.foscam.dto;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

@AutoValue
public abstract class AlarmNotifyTextDto implements FoscamTextDto {

    public static final FosInt16 RESERVE1 = FosInt16.create(0);
    public static final FosInt16 RESERVE2 = FosInt16.create(0);
    public static final FosInt16 RESERVE3 = FosInt16.create(0);
    public static final FosInt16 RESERVE4 = FosInt16.create(1);

    public abstract FosInt8 type();

    public abstract FosInt16 reserve1();

    public abstract FosInt16 reserve2();

    public abstract FosInt16 reserve3();

    public abstract FosInt16 reserve4();

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Alarm_Notify;
    }

    @Override
    public final int encodedLength() {
        return 1 + 2 + 2 + 2 + 2;
    }

    public static Builder builder() {
        return new AutoValue_AlarmNotifyTextDto.Builder()
                .reserve1(RESERVE1)
                .reserve2(RESERVE2)
                .reserve3(RESERVE3)
                .reserve4(RESERVE4);
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder type(FosInt8 type);

        public abstract Builder reserve1(FosInt16 reserve1);

        public abstract Builder reserve2(FosInt16 reserve2);

        public abstract Builder reserve3(FosInt16 reserve3);

        public abstract Builder reserve4(FosInt16 reserve4);

        public abstract AlarmNotifyTextDto build();
    }
}
