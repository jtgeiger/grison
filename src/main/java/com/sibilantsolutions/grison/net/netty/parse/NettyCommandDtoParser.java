package com.sibilantsolutions.grison.net.netty.parse;

import static com.sibilantsolutions.grison.net.netty.parse.NettyByteBufHelper.readBytes;

import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamOpCode;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamTextDto;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;
import io.netty.buffer.ByteBuf;

public class NettyCommandDtoParser {

    CommandDto parse(ByteBuf buf) {

        final byte[] protocolBytes = readBytes(4, buf);
        final FosInt16 operationCode = NettyFosTypeReader.fosInt16(buf);
        final FosInt8 reserve1 = NettyFosTypeReader.fosInt8(buf);
        final byte[] reserve2 = readBytes(8, buf);
        final FosInt32 textLength = NettyFosTypeReader.fosInt32(buf);
        final FosInt32 reserve3 = NettyFosTypeReader.fosInt32(buf);

        //TODO: Validate that there are textLength bytes remaining in the buf.

        ProtocolE p = ProtocolE.fromValue(protocolBytes);
        final FoscamOpCode foscamOpCode = FoscamOpCode.fromValue(p, operationCode.value);
        FoscamTextDto foscamTextDto = NettyFoscamTextParser.parse(foscamOpCode, buf);

        //TODO: Validate that all bytes are consumed from the buf.

        return new CommandDto(
                p,
                foscamOpCode,
                reserve1,
                reserve2,
                textLength,
                reserve3,
                foscamTextDto
        );
    }

}
