package com.sibilantsolutions.grison.driver.foscam.mapper;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.domain.AlarmTypeE;
import com.sibilantsolutions.grison.driver.foscam.domain.AudioFormatE;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.AlarmNotifyTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqAudioVideoTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.Unk02TextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.AlarmNotifyTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqAudioVideoTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqOperationTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.TalkDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.Unk02TextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoStartRespTextEntity;

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

    public static final Function<Unk02TextDto, Unk02TextEntity> unk02TextEntity = dto -> Unk02TextEntity.builder()
            .build();

    public static final Function<VideoStartReqTextDto, VideoStartReqTextEntity> videoStartReqTextEntity = dto -> VideoStartReqTextEntity.builder()
            .build();

    public static final Function<VideoStartRespTextDto, VideoStartRespTextEntity> videoStartRespTextEntity = dto -> {
        final VideoStartRespTextEntity.Builder builder = VideoStartRespTextEntity.builder()
                .result(ResultCodeE.fromValue(dto.result()));
        if (dto.dataConnectionId().isPresent()) {
            builder.dataConnectionId(dto.dataConnectionId().get());
        }
        return builder.build();
    };

    public static final Function<AlarmNotifyTextDto, AlarmNotifyTextEntity> alarmNotifyTextEntity = dto -> AlarmNotifyTextEntity.builder()
            .alarmType(AlarmTypeE.fromValue(dto.type()))
            .build();

    public static final Function<LoginReqAudioVideoTextDto, LoginReqAudioVideoTextEntity> loginReqAudioVideoTextEntity = dto -> LoginReqAudioVideoTextEntity.builder()
            .dataConnectionId(dto.dataConnectionId())
            .build();

    public static final Function<VideoDataTextDto, VideoDataTextEntity> videoDataTextEntity = dto -> VideoDataTextEntity.builder()
            .uptime(Duration.ofMillis(dto.timestamp().value() * 10))
            .timestamp(Instant.ofEpochSecond(dto.framePerSec().value()))
            .videoData(dto.videoData())
            .build();

    public static final Function<AudioDataTextDto, AudioDataTextEntity> audioDataTextEntity = dto -> AudioDataTextEntity.builder()
            .uptime(Duration.ofMillis(dto.timestampHundredths().value() * 10))
            .serialNumber(dto.snOfPacket().value())
            .timestamp(Instant.ofEpochSecond(dto.gatherTimeSecs().value()))
            .audioFormat(AudioFormatE.fromValue(dto.audioFormat()))
            .data(dto.data())
            .build();

    public static final Function<TalkDataTextDto, TalkDataTextEntity> talkDataTextEntity = dto -> TalkDataTextEntity.builder()
            .uptime(Duration.ofMillis(dto.timestampMs().value()))
            .serialNumber(dto.snOfPacket().value())
            .timestamp(Instant.ofEpochSecond(dto.gatherTimeSecs().value()))
            .audioFormat(AudioFormatE.fromValue(dto.audioFormat()))
            .data(dto.data())
            .build();

}
