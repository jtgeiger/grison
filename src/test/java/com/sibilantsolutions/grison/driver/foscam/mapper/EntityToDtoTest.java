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

public class EntityToDtoTest {

    private static final ResourceToByteBuf RESOURCE_TO_BYTE_BUF = new ResourceToByteBuf();
    private static final UnaryOperator<ByteBuf> BYTE_BUF_BYTE_BUF_FUNCTION = buf -> buf.readerIndex(buf.readerIndex() + CommandDto.COMMAND_PREFIX_LENGTH);

    @Test
    public void verifyRespTextDto() {
        final VerifyRespTextEntity entity = VerifyRespTextEntity.builder().resultCode(ResultCodeE.CORRECT).build();

        final VerifyRespTextDto actual = EntityToDto.verifyRespTextDto.apply(entity);

        final Function<String, VerifyRespTextDto> stringToDto = RESOURCE_TO_BYTE_BUF
                .andThen(BYTE_BUF_BYTE_BUF_FUNCTION)
                .andThen(new VerifyRespTextDtoParser());

        final VerifyRespTextDto expected = stringToDto.apply("/samples/verify_resp.bin");

        assertEquals(expected, actual);
    }

    @Test
    public void verifyRespTextDto_userWrong() {
        final VerifyRespTextEntity entity = VerifyRespTextEntity.builder().resultCode(ResultCodeE.USER_WRONG).build();

        final VerifyRespTextDto actual = EntityToDto.verifyRespTextDto.apply(entity);

        final Function<String, VerifyRespTextDto> stringToDto = RESOURCE_TO_BYTE_BUF
                .andThen(BYTE_BUF_BYTE_BUF_FUNCTION)
                .andThen(new VerifyRespTextDtoParser());

        final VerifyRespTextDto expected = stringToDto.apply("/samples/verify_resp_USER_WRONG.bin");

        assertEquals(expected, actual);
    }

}