package com.sibilantsolutions.grison.driver.foscam.mapper;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqOperationTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyRespTextEntity;

public final class DtoToEntity {

    private DtoToEntity() {
    }

    public static final Function<LoginReqOperationTextDto, LoginReqOperationTextEntity> loginReqOperationTextEntity = dto -> LoginReqOperationTextEntity.builder().build();

    public static final Function<LoginRespTextDto, LoginRespTextEntity> loginRespTextEntity = new LoginRespTextMapper();

    public static final Function<VerifyReqTextDto, VerifyReqTextEntity> verifyReqTextEntity = dto -> {
        final String username = new String(dto.user(), StandardCharsets.ISO_8859_1);
        final String password = new String(dto.password(), StandardCharsets.ISO_8859_1);
        return VerifyReqTextEntity.builder()
                .username(username.substring(0, username.indexOf(0)))
                .password(password.substring(0, password.indexOf(0)))
                .build();
    };

    public static final Function<VerifyRespTextDto, VerifyRespTextEntity> verifyRespTextEntity = dto -> VerifyRespTextEntity.builder()
            .resultCode(ResultCodeE.fromValue(dto.resultCode()))
            .build();

}
