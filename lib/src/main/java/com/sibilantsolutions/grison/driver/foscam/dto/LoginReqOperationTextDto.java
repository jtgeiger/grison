package com.sibilantsolutions.grison.driver.foscam.dto;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class LoginReqOperationTextDto implements FoscamTextDto {

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Login_Req_Operation;
    }

    @Override
    public final int encodedLength() {
        return 0;
    }

    public static Builder builder() {
        return new AutoValue_LoginReqOperationTextDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract LoginReqOperationTextDto build();
    }
}
