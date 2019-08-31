package com.sibilantsolutions.grison.driver.foscam.mapper;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.junit.Test;

import com.google.common.primitives.UnsignedInteger;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;
import com.sibilantsolutions.grison.net.netty.codec.VerifyRespTextDtoParser;
import com.sibilantsolutions.grison.net.netty.codec.VideoDataTextDtoParser;
import com.sibilantsolutions.grison.net.netty.codec.parse.ResourceToByteBuf;
import io.netty.buffer.ByteBuf;

public class EntityToDtoTest {

    private static final ResourceToByteBuf RESOURCE_TO_BYTE_BUF = new ResourceToByteBuf();

    /**
     * Function that skips the command prefix so that it is ready to read the data payload of the command.
     */
    private static final UnaryOperator<ByteBuf> SKIP_COMMAND_FUNCTION = buf ->
            buf.readerIndex(buf.readerIndex() + CommandDto.COMMAND_PREFIX_LENGTH);

    @Test
    public void verifyRespTextDto() {
        final VerifyRespTextEntity entity = VerifyRespTextEntity.builder().resultCode(ResultCodeE.CORRECT).build();

        final VerifyRespTextDto actual = EntityToDto.verifyRespTextDto.apply(entity);

        final Function<String, VerifyRespTextDto> resourceToDto = RESOURCE_TO_BYTE_BUF
                .andThen(SKIP_COMMAND_FUNCTION)
                .andThen(new VerifyRespTextDtoParser());

        final VerifyRespTextDto expected = resourceToDto.apply("/samples/verify_resp.bin");

        assertEquals(expected, actual);
    }

    @Test
    public void verifyRespTextDto_userWrong() {
        final VerifyRespTextEntity entity = VerifyRespTextEntity.builder().resultCode(ResultCodeE.USER_WRONG).build();

        final VerifyRespTextDto actual = EntityToDto.verifyRespTextDto.apply(entity);

        final Function<String, VerifyRespTextDto> resourceToDto = RESOURCE_TO_BYTE_BUF
                .andThen(SKIP_COMMAND_FUNCTION)
                .andThen(new VerifyRespTextDtoParser());

        final VerifyRespTextDto expected = resourceToDto.apply("/samples/verify_resp_USER_WRONG.bin");

        assertEquals(expected, actual);
    }

    @Test
    public void videoDataText() {
        final VideoDataTextEntity entity = VideoDataTextEntity
                .builder()
                .uptime(Duration.ofMillis(59_108L * 10))
                .timestamp(Instant.ofEpochMilli(1_400_688_245L * 1000))
                .videoData(new byte[44_600])
                .build();

        final VideoDataTextDto actual = EntityToDto.videoDataTextDto.apply(entity);

        final Function<String, VideoDataTextDto> resourceToDto = RESOURCE_TO_BYTE_BUF
                .andThen(SKIP_COMMAND_FUNCTION)
                .andThen(new VideoDataTextDtoParser());

        final VideoDataTextDto expected = resourceToDto.apply("/samples/video_data.bin");

        assertEquals(expected.timestampHundredths(), actual.timestampHundredths());
        assertEquals(expected.framePerSec(), actual.framePerSec());
        assertEquals(expected.reserve(), actual.reserve());
        assertEquals(expected.videoLength(), actual.videoLength());
        //Opting not to validate the data array.

    }

    @Test
    public void videoDataText2() {
        final VideoDataTextEntity entity = VideoDataTextEntity
                .builder()
                .uptime(Duration.ofMillis(4_230_881_480L))
                .timestamp(Instant.ofEpochMilli(1_566_749_828_000L))
                .videoData(new byte[39_436])
                .build();

        final VideoDataTextDto actual = EntityToDto.videoDataTextDto.apply(entity);

        final Function<String, VideoDataTextDto> resourceToDto = RESOURCE_TO_BYTE_BUF
                .andThen(SKIP_COMMAND_FUNCTION)
                .andThen(new VideoDataTextDtoParser());

        final VideoDataTextDto expected = resourceToDto.apply("/samples/video_data2.bin");

        assertEquals(expected.timestampHundredths(), actual.timestampHundredths());
        assertEquals(expected.framePerSec(), actual.framePerSec());
        assertEquals(expected.reserve(), actual.reserve());
        assertEquals(expected.videoLength(), actual.videoLength());
        //Opting not to validate the data array.

    }

    @Test
    public void videoDataText_large() {
        final VideoDataTextEntity entity = VideoDataTextEntity
                .builder()
                .uptime(Duration.ofMillis(22_895_263_570L))
                .timestamp(Instant.parse("2051-05-03T18:03:48Z"))
                .videoData(new byte[0])
                .build();

        final VideoDataTextDto actual = EntityToDto.videoDataTextDto.apply(entity);

        final VideoDataTextDto expected = VideoDataTextDto
                .builder()
                .timestampHundredths(FosInt32.create(UnsignedInteger.fromIntBits(0x88776655)))
                .framePerSec(FosInt32.create(UnsignedInteger.valueOf("2566749828")))
                .videoLength(FosInt32.create(UnsignedInteger.ZERO))
                .videoData(new byte[0])
                .reserve(FosInt8.create(0))
                .build();

        assertEquals(expected.timestampHundredths(), actual.timestampHundredths());
        assertEquals(expected.framePerSec(), actual.framePerSec());
        assertEquals(expected.reserve(), actual.reserve());
        assertEquals(expected.videoLength(), actual.videoLength());
        //Opting not to validate the data array.

    }

}