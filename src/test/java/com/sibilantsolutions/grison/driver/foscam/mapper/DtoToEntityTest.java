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

public class DtoToEntityTest {

    private static final ResourceToByteBuf RESOURCE_TO_BYTE_BUF = new ResourceToByteBuf();

    /**
     * Function that skips the command prefix so that it is ready to read the data payload of the command.
     */
    private static final UnaryOperator<ByteBuf> SKIP_COMMAND_FUNCTION = buf ->
            buf.readerIndex(buf.readerIndex() + CommandDto.COMMAND_PREFIX_LENGTH);

    @Test
    public void verifyRespTextEntity() {
        final Function<String, VerifyRespTextDto> resourceToDto = RESOURCE_TO_BYTE_BUF
                .andThen(SKIP_COMMAND_FUNCTION)
                .andThen(new VerifyRespTextDtoParser());

        final VerifyRespTextDto text = resourceToDto.apply("/samples/verify_resp.bin");

        final VerifyRespTextEntity actual = DtoToEntity.verifyRespTextEntity.apply(text);

        final VerifyRespTextEntity expected = VerifyRespTextEntity.builder()
                .resultCode(ResultCodeE.CORRECT)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    public void verifyRespTextEntity_userWrong() {
        final Function<String, VerifyRespTextDto> resourceToDto = RESOURCE_TO_BYTE_BUF
                .andThen(SKIP_COMMAND_FUNCTION)
                .andThen(new VerifyRespTextDtoParser());

        final VerifyRespTextDto text = resourceToDto.apply("/samples/verify_resp_USER_WRONG.bin");

        final VerifyRespTextEntity actual = DtoToEntity.verifyRespTextEntity.apply(text);

        final VerifyRespTextEntity expected = VerifyRespTextEntity.builder()
                .resultCode(ResultCodeE.USER_WRONG)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    public void videoDataText() {
        final Function<String, VideoDataTextDto> resourceToDto = RESOURCE_TO_BYTE_BUF
                .andThen(SKIP_COMMAND_FUNCTION)
                .andThen(new VideoDataTextDtoParser());

        final VideoDataTextDto text = resourceToDto.apply("/samples/video_data.bin");

        assertEquals(0xE6E4, text.timestampHundredths().value().longValue());   //59_108
        assertEquals(0x537CCE75, text.framePerSec().value().longValue());   //1_400_688_245
        assertEquals(0xAE38, text.videoLength().value().longValue());   //44_600
        assertEquals(text.videoLength().value().longValue(), text.videoData().length);

        final VideoDataTextEntity actual = DtoToEntity.videoDataTextEntity.apply(text);

        assertEquals(Duration.ofMillis(591_080), actual.uptime());
        assertEquals(Instant.ofEpochMilli(1_400_688_245_000L), actual.timestamp());
        assertEquals(44_600, actual.videoData().length);

    }

    @Test
    public void videoDataText2() {
        final Function<String, VideoDataTextDto> resourceToDto = RESOURCE_TO_BYTE_BUF
                .andThen(SKIP_COMMAND_FUNCTION)
                .andThen(new VideoDataTextDtoParser());

        final VideoDataTextDto text = resourceToDto.apply("/samples/video_data2.bin");

        assertEquals(0x1937D014, text.timestampHundredths().value().longValue());   //423_088_148
        assertEquals(0x5D62B484, text.framePerSec().value().longValue());   //1_566_749_828
        assertEquals(0x9A0C, text.videoLength().value().longValue());   //39_436
        assertEquals(text.videoLength().value().longValue(), text.videoData().length);

        final VideoDataTextEntity actual = DtoToEntity.videoDataTextEntity.apply(text);

        assertEquals(Duration.ofMillis(4230881480L), actual.uptime());
        assertEquals(Instant.ofEpochMilli(1_566_749_828_000L), actual.timestamp());
        assertEquals(39_436, actual.videoData().length);
    }

    @Test
    public void videoDataText_large() {

        final VideoDataTextDto text = VideoDataTextDto
                .builder()
                .timestampHundredths(FosInt32.create(0x88776655))
                .framePerSec(FosInt32.create(Integer.parseUnsignedInt("2566749828")))
                .videoLength(FosInt32.create(0))
                .videoData(new byte[0])
                .reserve(FosInt8.create(0))
                .build();

        assertEquals(UnsignedInteger.valueOf("2289526357"), text.timestampHundredths().value());   //2_289_526_357
        assertEquals(UnsignedInteger.valueOf(Integer.toUnsignedLong(0x98FD7E84)), text.framePerSec().value());   //2_566_749_828
        assertEquals(Integer.toUnsignedLong(0x98FD7E84), text.framePerSec().value().longValue());   //2_566_749_828
        assertEquals(0, text.videoLength().value().longValue());
        assertEquals(text.videoLength().value().longValue(), text.videoData().length);

        final VideoDataTextEntity actual = DtoToEntity.videoDataTextEntity.apply(text);

        assertEquals(Duration.ofMillis(22_895_263_570L), actual.uptime());
        assertEquals(Instant.parse("2051-05-03T18:03:48Z"), actual.timestamp());
        assertEquals(0, actual.videoData().length);
    }

}