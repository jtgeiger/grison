package com.sibilantsolutions.grison.driver.foscam.mapper;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

import com.google.common.primitives.Ints;
import com.sibilantsolutions.grison.driver.foscam.domain.AlarmTypeE;
import com.sibilantsolutions.grison.driver.foscam.domain.AudioFormatE;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.AlarmNotifyTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqAudioVideoTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.SearchReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.SearchRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.Unk02TextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoEndTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.AlarmNotifyTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioStartRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqAudioVideoTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqOperationTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.SearchReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.SearchRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.TalkDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.TalkStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.TalkStartRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.Unk02TextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VersionEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoEndTextEntity;
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

    public static final Function<VideoEndTextDto, VideoEndTextEntity> videoEndTextEntity = dto -> VideoEndTextEntity.builder().build();

    public static final Function<AudioStartReqTextDto, AudioStartReqTextEntity> audioStartReqTextEntity = dto -> AudioStartReqTextEntity.builder()
            .build();

    public static final Function<AudioStartRespTextDto, AudioStartRespTextEntity> audioStartRespTextEntity = dto -> {
        final AudioStartRespTextEntity.Builder builder = AudioStartRespTextEntity.builder()
                .result(ResultCodeE.fromValue(dto.result()));
        if (dto.dataConnectionId().isPresent()) {
            builder.dataConnectionId(dto.dataConnectionId().get());
        }
        return builder.build();
    };

    public static final Function<TalkStartReqTextDto, TalkStartReqTextEntity> talkStartReqTextEntity = dto -> TalkStartReqTextEntity.builder()
            .build();

    public static final Function<TalkStartRespTextDto, TalkStartRespTextEntity> talkStartRespTextEntity = dto -> {
        final TalkStartRespTextEntity.Builder builder = TalkStartRespTextEntity.builder()
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
            .uptime(Duration.ofMillis(dto.timestampHundredths().value() * 10))
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

    public static final Function<SearchReqTextDto, SearchReqTextEntity> searchReqTextEntity = dto -> SearchReqTextEntity.builder()
            .build();

    public static final Function<SearchRespTextDto, SearchRespTextEntity> searchRespTextEntity = dto -> {
        final String cameraId = new String(dto.cameraId(), StandardCharsets.ISO_8859_1);
        final String cameraName = new String(dto.cameraName(), StandardCharsets.ISO_8859_1);

        ByteBuffer sysSoftwareVersionBuf = ByteBuffer.wrap(dto.sysSoftwareVersion());

        VersionEntity sysSoftwareVersion = VersionEntity.builder()
                .major(sysSoftwareVersionBuf.get())
                .minor(sysSoftwareVersionBuf.get())
                .patch(sysSoftwareVersionBuf.get())
                .buildNum(sysSoftwareVersionBuf.get())
                .build();

        ByteBuffer appSoftwareVersionBuf = ByteBuffer.wrap(dto.appSoftwareVersion());

        VersionEntity appSoftwareVersion = VersionEntity.builder()
                .major(appSoftwareVersionBuf.get())
                .minor(appSoftwareVersionBuf.get())
                .patch(appSoftwareVersionBuf.get())
                .buildNum(appSoftwareVersionBuf.get())
                .build();

        final InetSocketAddress address;
        final InetAddress mask;
        final InetAddress gateway;
        final InetAddress dns;

        try {
            address = new InetSocketAddress(InetAddress.getByAddress(Ints.toByteArray(dto.ip().value())), dto.cameraPort().value());
            mask = InetAddress.getByAddress(Ints.toByteArray(dto.mask().value()));
            gateway = InetAddress.getByAddress(Ints.toByteArray(dto.gateway().value()));
            dns = InetAddress.getByAddress(Ints.toByteArray(dto.dns().value()));
        } catch (UnknownHostException e) {
            throw new UnsupportedOperationException("TODO (CSB)", e);
        }

        return SearchRespTextEntity.builder()
                .cameraId(cameraId.substring(0, cameraId.indexOf(0)))
                .cameraName(cameraName.substring(0, cameraName.indexOf(0)))
                .address(address)
                .mask(mask)
                .gateway(gateway)
                .dns(dns)
                .sysSoftwareVersion(sysSoftwareVersion)
                .appSoftwareVersion(appSoftwareVersion)
                .isDhcpEnabled(dto.dhcpEnabled().value() == 1)
                .build();
    };

}
