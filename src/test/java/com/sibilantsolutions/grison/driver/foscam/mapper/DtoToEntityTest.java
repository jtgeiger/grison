package com.sibilantsolutions.grison.driver.foscam.mapper;

import static org.junit.Assert.assertEquals;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.junit.Test;

import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyRespTextEntity;
import com.sibilantsolutions.grison.net.netty.codec.VerifyRespTextDtoParser;
import com.sibilantsolutions.grison.net.netty.codec.parse.ResourceToByteBuf;
import io.netty.buffer.ByteBuf;

public class DtoToEntityTest {

    private static final ResourceToByteBuf RESOURCE_TO_BYTE_BUF = new ResourceToByteBuf();
    private static final UnaryOperator<ByteBuf> BYTE_BUF_BYTE_BUF_FUNCTION = buf -> buf.readerIndex(buf.readerIndex() + CommandDto.COMMAND_PREFIX_LENGTH);

    @Test
    public void verifyRespTextEntity() {
        final Function<String, VerifyRespTextDto> stringToDto = RESOURCE_TO_BYTE_BUF
                .andThen(BYTE_BUF_BYTE_BUF_FUNCTION)
                .andThen(new VerifyRespTextDtoParser());

        final VerifyRespTextDto text = stringToDto.apply("/samples/verify_resp.bin");

        final VerifyRespTextEntity actual = DtoToEntity.verifyRespTextEntity.apply(text);

        final VerifyRespTextEntity expected = VerifyRespTextEntity.builder()
                .resultCode(ResultCodeE.CORRECT)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    public void verifyRespTextEntity_userWrong() {
        final Function<String, VerifyRespTextDto> stringToDto = RESOURCE_TO_BYTE_BUF
                .andThen(BYTE_BUF_BYTE_BUF_FUNCTION)
                .andThen(new VerifyRespTextDtoParser());

        final VerifyRespTextDto text = stringToDto.apply("/samples/verify_resp_USER_WRONG.bin");

        final VerifyRespTextEntity actual = DtoToEntity.verifyRespTextEntity.apply(text);

        final VerifyRespTextEntity expected = VerifyRespTextEntity.builder()
                .resultCode(ResultCodeE.USER_WRONG)
                .build();

        assertEquals(expected, actual);
    }

}