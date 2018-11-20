package com.sibilantsolutions.grison.driver.foscam.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VersionEntity;
import com.sibilantsolutions.grison.net.netty.codec.NettyFoscamTextParser;
import com.sibilantsolutions.grison.net.netty.codec.parse.ResourceToByteBuf;
import io.netty.buffer.ByteBuf;

public class LoginRespTextMapperTest {

    @Test
    public void apply() {

        final ByteBuf byteBuf = new ResourceToByteBuf().apply("/samples/login_resp.bin");
        byteBuf.readerIndex(4 + 2 + 1 + 8 + 4 + 4); //Skip ahead to the text.
        final LoginRespTextDto dto = NettyFoscamTextParser.loginRespDto(byteBuf);
        final LoginRespTextEntity entity = new LoginRespTextMapper().apply(dto);
        assertEquals(ResultCodeE.CORRECT, entity.resultCode());
        assertEquals(Optional.of("00626E4E72BF"), entity.cameraId());
        final VersionEntity expected = VersionEntity.builder().major(11).minor(37).patch(2).buildNum(56).build();
        assertEquals(Optional.of(expected), entity.version());
    }
}