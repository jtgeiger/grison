package com.sibilantsolutions.grison.net.netty.parse;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.io.Resources;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyFoscamTextParserTest {

    @Test
    public void loginRespDto() throws Exception {
        final byte[] bytes = Resources.toByteArray(Resources.getResource("samples/login_resp.bin"));
        final ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        byteBuf.readerIndex(4 + 2 + 1 + 8 + 4 + 4); //Skip ahead to the text.
        final LoginRespTextDto dto = NettyFoscamTextParser.loginRespDto(byteBuf);
        assertEquals(0, dto.resultCode().value);
        assertEquals(13, dto.cameraId().get().length);
        assertEquals(4, dto.reserve1().get().length);
        assertEquals(4, dto.reserve2().get().length);
        assertEquals(4, dto.firmwareVersion().get().length);
    }
}