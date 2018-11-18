package com.sibilantsolutions.grison.driver.foscam.entity.mapper;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VersionEntity;

public final class LoginRespTextMapper {

    private LoginRespTextMapper() {
    }

    public static LoginRespTextEntity map(LoginRespTextDto dto) {
        final LoginRespTextEntity.Builder builder = LoginRespTextEntity.builder();

        builder.resultCode(ResultCodeE.fromValue(dto.resultCode().value));

        if (dto.cameraId().isPresent()) {
            builder.cameraId(new String(dto.cameraId().get(), StandardCharsets.ISO_8859_1));
        }

        if (dto.firmwareVersion().isPresent()) {
            ByteBuffer buf = ByteBuffer.wrap(dto.firmwareVersion().get());
            builder.version(VersionEntity.builder()
                    .major(buf.get())
                    .major(buf.get())
                    .patch(buf.get())
                    .buildNum(buf.get())
                    .build());
        }

        return builder.build();
    }
}
