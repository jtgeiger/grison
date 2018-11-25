package com.sibilantsolutions.grison.net.netty.codec;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import org.junit.Test;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import com.sibilantsolutions.grison.net.netty.codec.parse.ResourceToByteBuf;

public class VerifyReqTextDtoParserTest {

    @Test
    public void apply() {

        final Function<String, VerifyReqTextDto> stringToDto = new ResourceToByteBuf()
                .andThen(buf -> buf.readerIndex(buf.readerIndex() + CommandDto.COMMAND_PREFIX_LENGTH))
                .andThen(new VerifyReqTextDtoParser());

        final VerifyReqTextDto expected = stringToDto.apply("/samples/verify_req.bin");

        final VerifyReqTextDto actual = VerifyReqTextDto.builder()
                .user("camvis\0\0\0\0\0\0\0".getBytes(StandardCharsets.ISO_8859_1))
                .password("vis,FOSbuy1v\0".getBytes(StandardCharsets.ISO_8859_1))
                .build();
        assertEquals(expected, actual);
    }
}