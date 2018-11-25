package com.sibilantsolutions.grison.driver.foscam.dto;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VerifyReqTextDto implements FoscamTextDto {

    public static final int USER_LEN = 13;
    public static final int PASSWORD_LEN = 13;

    public abstract byte[] user();

    public abstract byte[] password();

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Verify_Req;
    }

    @Override
    public final int encodedLength() {
        return USER_LEN + PASSWORD_LEN;
    }

    public static Builder builder() {
        return new AutoValue_VerifyReqTextDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder user(byte[] user);

        public abstract Builder password(byte[] password);

        abstract VerifyReqTextDto autoBuild();  //Not public

        public VerifyReqTextDto build() {
            final VerifyReqTextDto dto = autoBuild();

            checkArgument(dto.user().length == USER_LEN, "user len expected=%s actual=%s", USER_LEN, dto.user().length);
            checkArgument(dto.user()[USER_LEN - 1] == 0, "user should end with null byte actual=%s", dto.user()[USER_LEN - 1]);
            checkArgument(dto.password().length == PASSWORD_LEN, "password len expected=%s actual=%s", PASSWORD_LEN, dto.password().length);
            checkArgument(dto.password()[PASSWORD_LEN - 1] == 0, "user should end with null byte actual=%s", dto.password()[PASSWORD_LEN - 1]);

            return dto;
        }
    }
}
