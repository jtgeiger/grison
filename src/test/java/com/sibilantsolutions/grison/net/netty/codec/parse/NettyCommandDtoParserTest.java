package com.sibilantsolutions.grison.net.netty.codec.parse;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import java.time.Clock;

import org.junit.Test;

import com.google.common.primitives.UnsignedInteger;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamOpCode;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;
import com.sibilantsolutions.grison.net.netty.codec.NettyCommandDtoParser;
import io.netty.buffer.ByteBuf;

public class NettyCommandDtoParserTest {

    @Test
    public void parse() {
        final ByteBuf buf = new ResourceToByteBuf().apply("/samples/login_resp.bin");
        final CommandDto dto = new NettyCommandDtoParser(Clock.systemUTC()).parse(buf);
        assertEquals(ProtocolE.OPERATION_PROTOCOL, dto.protocol());
        assertEquals(FoscamOpCode.Login_Resp, dto.operationCode());
        assertEquals(FosInt8.create(0), dto.reserve1());
        assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, dto.reserve2());
        assertEquals(FosInt32.create(UnsignedInteger.valueOf(27)), dto.textLength());
        assertEquals(FosInt32.create(UnsignedInteger.valueOf(27)), dto.reserve3());
        assertEquals(LoginRespTextDto.builder()
                .resultCode(ResultCodeE.CORRECT.value)
                .loginRespDetails(LoginRespDetailsDto.builder()
                        .cameraId("00626E4E72BF\0".getBytes(StandardCharsets.ISO_8859_1))
                        .firmwareVersion(new byte[]{11, 37, 2, 56})
                        .build())
                .build(), dto.text());
    }

}