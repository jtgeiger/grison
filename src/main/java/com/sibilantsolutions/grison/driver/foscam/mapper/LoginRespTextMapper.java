package com.sibilantsolutions.grison.driver.foscam.mapper;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.google.common.base.VerifyException;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VersionEntity;

public final class LoginRespTextMapper implements DtoToEntityMapper<LoginRespTextDto, LoginRespTextEntity> {

    @Override
    public LoginRespTextEntity apply(LoginRespTextDto dto) {
        final LoginRespTextEntity.Builder builder = LoginRespTextEntity.builder();

        final ResultCodeE resultCode = ResultCodeE.fromValue(dto.resultCode().value());
        builder.resultCode(resultCode);

        if (resultCode == ResultCodeE.CORRECT) {
            final LoginRespDetailsDto loginRespDetailsDto = dto.loginRespDetails().orElseThrow(VerifyException::new);

            final String cameraId = new String(loginRespDetailsDto.cameraId(), StandardCharsets.ISO_8859_1);

            //Original array holds a null-terminated string so right-trim.
            builder.cameraId(cameraId.substring(0, cameraId.indexOf(0)));

            ByteBuffer buf = ByteBuffer.wrap(loginRespDetailsDto.firmwareVersion());

            builder.version(VersionEntity.builder()
                    .major(buf.get())
                    .minor(buf.get())
                    .patch(buf.get())
                    .buildNum(buf.get())
                    .build());
        }

        return builder.build();
    }

}
