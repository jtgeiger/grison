package com.sibilantsolutions.grison.net.netty.parse;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.google.common.base.VerifyException;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import io.netty.buffer.ByteBuf;

public class NettyFoscamTextParserTest {

    @Test
    public void loginRespDto() {
        final ByteBuf byteBuf = new ResourceToByteBuf().apply("/samples/login_resp.bin");
        byteBuf.readerIndex(4 + 2 + 1 + 8 + 4 + 4); //Skip ahead to the text.
        final LoginRespTextDto dto = NettyFoscamTextParser.loginRespDto(byteBuf);
        assertEquals(0, dto.resultCode().value());
        final LoginRespDetailsDto loginRespDetailsDto = dto.loginRespDetails().orElseThrow(VerifyException::new);
        assertArrayEquals("00626E4E72BF\0".getBytes(StandardCharsets.ISO_8859_1), loginRespDetailsDto.cameraId());
        assertArrayEquals(new byte[]{0, 0, 0, 1}, loginRespDetailsDto.reserve1());
        assertArrayEquals(new byte[]{0, 0, 0, 0}, loginRespDetailsDto.reserve2());
        assertArrayEquals(new byte[]{11, 37, 2, 56}, loginRespDetailsDto.firmwareVersion());
    }
}