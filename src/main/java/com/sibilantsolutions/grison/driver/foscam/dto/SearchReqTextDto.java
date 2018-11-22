package com.sibilantsolutions.grison.driver.foscam.dto;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

@AutoValue
public abstract class SearchReqTextDto implements FoscamTextDto {

    public static final FosInt8 reserve1 = FosInt8.create(0);
    public static final FosInt8 reserve2 = FosInt8.create(0);
    public static final FosInt8 reserve3 = FosInt8.create(0);
    public static final FosInt8 reserve4 = FosInt8.create(1);

    public abstract FosInt8 reserve1();

    public abstract FosInt8 reserve2();

    public abstract FosInt8 reserve3();

    public abstract FosInt8 reserve4();

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Search_Req;
    }

    @Override
    public final int encodedLength() {
        return 4;
    }

    public static Builder builder() {
        return new AutoValue_SearchReqTextDto.Builder()
                .reserve1(reserve1)
                .reserve2(reserve2)
                .reserve3(reserve3)
                .reserve4(reserve4);
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder reserve1(FosInt8 reserve1);

        public abstract Builder reserve2(FosInt8 reserve2);

        public abstract Builder reserve3(FosInt8 reserve3);

        public abstract Builder reserve4(FosInt8 reserve4);

        public abstract SearchReqTextDto build();
    }
}
