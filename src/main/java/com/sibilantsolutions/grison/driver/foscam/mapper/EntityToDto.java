package com.sibilantsolutions.grison.driver.foscam.mapper;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import com.google.common.base.Strings;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqOperationTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

public final class EntityToDto {

    private EntityToDto() {
    }

    public static final Function<LoginReqOperationTextEntity, LoginReqOperationTextDto> loginReqOperationTextDto = entity -> LoginReqOperationTextDto.builder()
            .build();

    public static final Function<LoginRespTextEntity, LoginRespTextDto> loginRespTextDto = entity -> LoginRespTextDto.builder()
            .build();

    public static final Function<VerifyReqTextEntity, VerifyReqTextDto> verifyReqTextDto = entity -> VerifyReqTextDto.builder()
            .user(Strings.padEnd(entity.username(), VerifyReqTextDto.USER_LEN, '\0').getBytes(StandardCharsets.ISO_8859_1))
            .password(Strings.padEnd(entity.password(), VerifyReqTextDto.PASSWORD_LEN, '\0').getBytes(StandardCharsets.ISO_8859_1))
            .build();

    public static final Function<VerifyRespTextEntity, VerifyRespTextDto> verifyRespTextDto = entity -> {
        final VerifyRespTextDto.Builder builder = VerifyRespTextDto.builder()
                .resultCode(entity.resultCode().value);
        if (entity.resultCode() == ResultCodeE.CORRECT) {
            builder.reserve(FosInt8.create(0));
        }
        return builder.build();
    };

}
