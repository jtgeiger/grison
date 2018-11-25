package com.sibilantsolutions.grison.net.netty.codec;

import static com.sibilantsolutions.grison.net.netty.codec.parse.NettyByteBufHelper.readBytes;

import java.util.function.Function;

import com.sibilantsolutions.grison.driver.foscam.dto.SearchRespTextDto;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public class SearchRespTextDtoParser implements Function<ByteBuf, SearchRespTextDto> {
    @Override
    public SearchRespTextDto apply(ByteBuf buf) {
        return SearchRespTextDto.builder()
                .cameraId(readBytes(SearchRespTextDto.CAMERA_ID_LEN, buf))
                .cameraName(readBytes(SearchRespTextDto.CAMERA_NAME_LEN, buf))
                .ip(NettyFosTypeReader.fosInt32R(buf))
                .mask(NettyFosTypeReader.fosInt32R(buf))
                .gateway(NettyFosTypeReader.fosInt32R(buf))
                .dns(NettyFosTypeReader.fosInt32R(buf))
                .reserve(readBytes(SearchRespTextDto.RESERVE.length, buf))
                .sysSoftwareVersion(readBytes(SearchRespTextDto.SYS_SOFTWARE_VERSION_LEN, buf))
                .appSoftwareVersion(readBytes(SearchRespTextDto.APP_SOFTWARE_VERSION_LEN, buf))
                .cameraPort(NettyFosTypeReader.fosInt16R(buf))
                .dhcpEnabled(NettyFosTypeReader.fosInt8(buf))
                .build();
    }
}
