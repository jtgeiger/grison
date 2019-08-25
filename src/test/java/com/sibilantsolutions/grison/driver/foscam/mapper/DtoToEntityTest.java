package com.sibilantsolutions.grison.driver.foscam.mapper;

import static org.junit.Assert.assertEquals;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.junit.Test;

import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyRespTextEntity;
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

        assertEquals(0xE6E4, text.timestampHundredths().value());   //59_108
        assertEquals(0x537CCE75, text.framePerSec().value());   //1_400_688_245
        assertEquals(0xAE38, text.videoLength().value());   //44_600
        assertEquals(text.videoLength().value(), text.videoData().length);
    }

    @Test
    public void videoDataText2() {
        final Function<String, VideoDataTextDto> resourceToDto = RESOURCE_TO_BYTE_BUF
                .andThen(SKIP_COMMAND_FUNCTION)
                .andThen(new VideoDataTextDtoParser());

        final VideoDataTextDto text = resourceToDto.apply("/samples/video_data2.bin");

        assertEquals(0x1937D014, text.timestampHundredths().value());   //423_088_148
        assertEquals(0x5D62B484, text.framePerSec().value());   //1_566_749_828
        assertEquals(0x9A0C, text.videoLength().value());   //39_436
        assertEquals(text.videoLength().value(), text.videoData().length);
    }

}