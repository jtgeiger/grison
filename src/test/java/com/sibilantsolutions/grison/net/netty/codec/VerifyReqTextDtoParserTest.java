package com.sibilantsolutions.grison.net.netty.codec;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.junit.Test;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import com.sibilantsolutions.grison.net.netty.codec.parse.ResourceToByteBuf;
import io.netty.buffer.ByteBuf;

public class VerifyReqTextDtoParserTest {

    private static final ResourceToByteBuf RESOURCE_TO_BYTE_BUF = new ResourceToByteBuf();
    private static final UnaryOperator<ByteBuf> BYTE_BUF_BYTE_BUF_FUNCTION = buf -> buf.readerIndex(buf.readerIndex() + CommandDto.COMMAND_PREFIX_LENGTH);

    @Test
    public void apply() {

        final Function<String, VerifyReqTextDto> stringToDto = RESOURCE_TO_BYTE_BUF
                .andThen(BYTE_BUF_BYTE_BUF_FUNCTION)
                .andThen(VerifyReqTextDtoParser.parse);

        final VerifyReqTextDto expected = stringToDto.apply("/samples/verify_req.bin");

        final VerifyReqTextDto actual = VerifyReqTextDto.builder()
                .user("camvis\0\0\0\0\0\0\0".getBytes(StandardCharsets.ISO_8859_1))
                .password("vis,FOSbuy1v\0".getBytes(StandardCharsets.ISO_8859_1))
                .build();
        assertEquals(expected, actual);
    }
}