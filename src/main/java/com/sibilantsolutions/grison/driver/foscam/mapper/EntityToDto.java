package com.sibilantsolutions.grison.driver.foscam.mapper;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import com.google.common.base.Strings;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqAudioVideoTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.Unk02TextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqAudioVideoTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqOperationTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.Unk02TextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoStartRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
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

    public static final Function<Unk02TextEntity, Unk02TextDto> unk02TextDto = entity -> Unk02TextDto.builder()
            .data(new byte[Unk02TextDto.DATA_LEN])
            .build();

    public static final Function<VideoStartReqTextEntity, VideoStartReqTextDto> videoStartReqTextDto = entity -> VideoStartReqTextDto.builder()
            .build();

    public static final Function<VideoStartRespTextEntity, VideoStartRespTextDto> videoStartRespTextDto = entity -> {
        final VideoStartRespTextDto.Builder builder = VideoStartRespTextDto.builder()
                .result(entity.result().value);
        if (entity.dataConnectionId().isPresent()) {
            builder.dataConnectionId(entity.dataConnectionId().get());
        }
        return builder.build();
    };

    public static final Function<LoginReqAudioVideoTextEntity, LoginReqAudioVideoTextDto> loginReqAudioVideoTextDto = entity -> LoginReqAudioVideoTextDto.builder()
            .dataConnectionId(entity.dataConnectionId())
            .build();

    public static final Function<VideoDataTextEntity, VideoDataTextDto> videoDataTextDto = entity -> VideoDataTextDto.builder()
            .timestamp(FosInt32.create((int) (entity.uptimeMs() / 10)))
            .framePerSec(FosInt32.create((int) entity.timestamp().getEpochSecond()))
            .videoData(entity.videoData())
            .reserve(FosInt8.create(0))
            .build();

}
