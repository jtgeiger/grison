package com.sibilantsolutions.grison.driver.foscam.mapper;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.function.Function;

import com.google.common.base.Strings;
import com.google.common.primitives.UnsignedBytes;
import com.google.common.primitives.UnsignedInteger;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.AlarmNotifyTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioEndTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.InitReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.InitRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveAudioVideoTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqAudioVideoTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto;
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
import com.sibilantsolutions.grison.driver.foscam.entity.AudioEndTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioStartRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.InitReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.InitRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.KeepAliveAudioVideoTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.KeepAliveOperationTextEntity;
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
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16R;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32R;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

public final class EntityToDto {

    private EntityToDto() {
    }

    public static final Function<KeepAliveOperationTextEntity, KeepAliveOperationTextDto> keepAliveOperationTextDto = entity -> KeepAliveOperationTextDto.builder()
            .build();

    public static final Function<KeepAliveAudioVideoTextEntity, KeepAliveAudioVideoTextDto> keepAliveAudioVideoTextDto = entity -> KeepAliveAudioVideoTextDto.builder()
            .build();

    public static final Function<LoginReqOperationTextEntity, LoginReqOperationTextDto> loginReqOperationTextDto = entity -> LoginReqOperationTextDto.builder()
            .build();

    public static final Function<LoginRespTextEntity, LoginRespTextDto> loginRespTextDto = entity -> {
        final LoginRespTextDto.Builder builder = LoginRespTextDto.builder()
                .resultCode(entity.resultCode().value);
        if (entity.resultCode() == ResultCodeE.CORRECT) {
            final VersionEntity versionEntity = entity.version().orElseThrow(NoSuchElementException::new);
            builder.loginRespDetails(LoginRespDetailsDto.builder()
                    .cameraId(Strings.padEnd(entity.cameraId().orElseThrow(NoSuchElementException::new),
                            LoginRespDetailsDto.CAMERA_ID_LEN, '\0')
                            .getBytes(StandardCharsets.ISO_8859_1))
                    .firmwareVersion(versionToBytes(versionEntity))
                    .build());
        }
        return builder.build();
    };

    private static byte[] versionToBytes(VersionEntity versionEntity) {
        return new byte[]{
                        UnsignedBytes.checkedCast(versionEntity.major()),
                        UnsignedBytes.checkedCast(versionEntity.minor()),
                        UnsignedBytes.checkedCast(versionEntity.patch()),
                        UnsignedBytes.checkedCast(versionEntity.buildNum())};
    }

    public static final Function<VerifyReqTextEntity, VerifyReqTextDto> verifyReqTextDto = entity -> VerifyReqTextDto.builder()
            .user(Strings.padEnd(entity.username(), VerifyReqTextDto.USER_LEN, '\0').getBytes(StandardCharsets.ISO_8859_1))
            .password(Strings.padEnd(entity.password(), VerifyReqTextDto.PASSWORD_LEN, '\0').getBytes(StandardCharsets.ISO_8859_1))
            .build();

    public static final Function<VerifyRespTextEntity, VerifyRespTextDto> verifyRespTextDto = entity -> {
        final VerifyRespTextDto.Builder builder = VerifyRespTextDto.builder()
                .resultCode(entity.resultCode().value);
        if (entity.resultCode() == ResultCodeE.CORRECT) {
            builder.reserve(FosInt8.ZERO);
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

    public static final Function<VideoEndTextEntity, VideoEndTextDto> videoEndTextDto = entity -> VideoEndTextDto.builder().build();

    public static final Function<AudioStartReqTextEntity, AudioStartReqTextDto> audioStartReqTextDto = entity -> AudioStartReqTextDto.builder()
            .build();

    public static final Function<AudioStartRespTextEntity, AudioStartRespTextDto> audioStartRespTextDto = entity -> {
        final AudioStartRespTextDto.Builder builder = AudioStartRespTextDto.builder()
                .result(entity.result().value);
        if (entity.dataConnectionId().isPresent()) {
            builder.dataConnectionId(entity.dataConnectionId().get());
        }
        return builder.build();
    };

    public static final Function<AudioEndTextEntity, AudioEndTextDto> audioEndTextDto = entity -> AudioEndTextDto.builder().build();

    public static final Function<TalkStartReqTextEntity, TalkStartReqTextDto> talkStartReqTextDto = entity -> TalkStartReqTextDto.builder()
            .build();

    public static final Function<TalkStartRespTextEntity, TalkStartRespTextDto> talkStartRespTextDto = entity -> {
        final TalkStartRespTextDto.Builder builder = TalkStartRespTextDto.builder()
                .result(entity.result().value);
        if (entity.dataConnectionId().isPresent()) {
            builder.dataConnectionId(entity.dataConnectionId().get());
        }
        return builder.build();
    };

    public static final Function<AlarmNotifyTextEntity, AlarmNotifyTextDto> alarmNotifyTextDto = entity -> AlarmNotifyTextDto.builder()
            .type(entity.alarmType().value)
            .build();

    public static final Function<LoginReqAudioVideoTextEntity, LoginReqAudioVideoTextDto> loginReqAudioVideoTextDto = entity -> LoginReqAudioVideoTextDto.builder()
            .dataConnectionId(entity.dataConnectionId())
            .build();

    public static final Function<VideoDataTextEntity, VideoDataTextDto> videoDataTextDto = entity -> VideoDataTextDto.builder()
            .timestampHundredths(FosInt32.create(UnsignedInteger.valueOf(entity.uptime().toMillis() / 10)))
            .framePerSec(FosInt32.create(UnsignedInteger.valueOf(entity.timestamp().getEpochSecond())))
            .videoLength(FosInt32.create(UnsignedInteger.valueOf(entity.videoData().length)))
            .videoData(entity.videoData())
            .reserve(FosInt8.ZERO)
            .build();

    public static final Function<AudioDataTextEntity, AudioDataTextDto> audioDataTextDto = entity -> AudioDataTextDto.builder()
            .timestampHundredths(FosInt32.create(UnsignedInteger.valueOf(entity.uptime().toMillis() / 10)))
            .snOfPacket(FosInt32.create(UnsignedInteger.valueOf(entity.serialNumber())))
            .gatherTimeSecs(FosInt32.create(UnsignedInteger.valueOf(entity.timestamp().getEpochSecond())))
            .audioFormat(entity.audioFormat().value)
            .dataLength(FosInt32.create(UnsignedInteger.valueOf(entity.data().length)))
            .data(entity.data())
            .build();

    public static final Function<TalkDataTextEntity, TalkDataTextDto> talkDataTextDto = entity -> TalkDataTextDto.builder()
            .timestampMs(FosInt32.create(UnsignedInteger.valueOf(entity.uptime().toMillis())))
            .snOfPacket(FosInt32.create(UnsignedInteger.valueOf(entity.serialNumber())))
            .gatherTimeSecs(FosInt32.create(UnsignedInteger.valueOf(entity.timestamp().getEpochSecond())))
            .audioFormat(entity.audioFormat().value)
            .dataLength(FosInt32.create(UnsignedInteger.valueOf(entity.data().length)))
            .data(entity.data())
            .build();

    public static final Function<SearchReqTextEntity, SearchReqTextDto> searchReqTextDto = entity -> SearchReqTextDto.builder().build();

    public static final Function<SearchRespTextEntity, SearchRespTextDto> searchRespTextDto = entity -> SearchRespTextDto.builder()
            .cameraId(Strings.padEnd(entity.cameraId(), SearchRespTextDto.CAMERA_ID_LEN, '\0').getBytes(StandardCharsets.ISO_8859_1))
            .cameraName(Strings.padEnd(entity.cameraName(), SearchRespTextDto.CAMERA_NAME_LEN, '\0').getBytes(StandardCharsets.ISO_8859_1))
            .ip(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(entity.address().getAddress().getAddress()).getInt())))
            .mask(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(entity.mask().getAddress()).getInt())))
            .gateway(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(entity.gateway().getAddress()).getInt())))
            .dns(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(entity.dns().getAddress()).getInt())))
            .cameraPort(FosInt16R.create(Integer.valueOf(entity.address().getPort()).shortValue()))
            .dhcpEnabled(entity.isDhcpEnabled() ? FosInt8.ONE : FosInt8.ZERO)
            .sysSoftwareVersion(versionToBytes(entity.sysSoftwareVersion()))
            .appSoftwareVersion(versionToBytes(entity.appSoftwareVersion()))
            .build();

    public static final Function<InitReqTextEntity, InitReqTextDto> initReqTextDto = entity -> InitReqTextDto.builder()
            .cameraId(Strings.padEnd(entity.cameraId(), SearchRespTextDto.CAMERA_ID_LEN, '\0').getBytes(StandardCharsets.ISO_8859_1))
            .user(Strings.padEnd(entity.username(), VerifyReqTextDto.USER_LEN, '\0').getBytes(StandardCharsets.ISO_8859_1))
            .password(Strings.padEnd(entity.password(), VerifyReqTextDto.PASSWORD_LEN, '\0').getBytes(StandardCharsets.ISO_8859_1))
            .ip(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(entity.address().getAddress().getAddress()).getInt())))
            .cameraPort(FosInt16R.create(Integer.valueOf(entity.address().getPort()).shortValue()))
            .mask(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(entity.mask().getAddress()).getInt())))
            .gateway(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(entity.gateway().getAddress()).getInt())))
            .dns(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(entity.dns().getAddress()).getInt())))
            .build();

    public static final Function<InitRespTextEntity, InitRespTextDto> initRespTextDto = entity -> InitRespTextDto.builder()
            .resultCode(entity.result().value)
            .build();

}
