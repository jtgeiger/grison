package com.sibilantsolutions.grison.net.netty.parse;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.io.Resources;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyFoscamTextDtoParserTest {

    @Test
    public void loginRespDto() throws Exception {
        final byte[] bytes = Resources.toByteArray(Resources.getResource("samples/login_resp.bin"));
        final ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        final LoginRespTextDto dto = NettyFoscamTextParser.loginRespDto(byteBuf);
//        assertThat(dto.cameraId().length, eq(13));
        assertEquals(13, dto.cameraId().length);
    }
}