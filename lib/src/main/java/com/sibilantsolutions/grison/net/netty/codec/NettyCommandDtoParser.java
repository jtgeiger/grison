package com.sibilantsolutions.grison.net.netty.codec;

import static com.google.common.base.Verify.verify;
import static com.sibilantsolutions.grison.driver.foscam.dto.CommandDto.PROTOCOL_LEN;
import static com.sibilantsolutions.grison.driver.foscam.dto.CommandDto.RESERVE2;
import static com.sibilantsolutions.grison.net.netty.codec.parse.NettyByteBufHelper.readBytes;

import java.time.Clock;
import java.time.Instant;

import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamOpCode;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamTextDto;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;
import com.sibilantsolutions.grison.net.netty.codec.parse.NettyFosTypeReader;
import io.netty.buffer.ByteBuf;

public class NettyCommandDtoParser {

    private final Clock clock;

    public NettyCommandDtoParser(Clock clock) {
        this.clock = clock;
    }

    public CommandDto parse(ByteBuf buf) {

        // Record the local timestamp because the server's timestamp may be off by a little or a lot.
        final Instant clientTimestamp = clock.instant();

        final byte[] protocolBytes = readBytes(PROTOCOL_LEN, buf);
        final FosInt16 operationCode = NettyFosTypeReader.fosInt16(buf);
        final FosInt8 reserve1 = NettyFosTypeReader.fosInt8(buf);
        final byte[] reserve2 = readBytes(RESERVE2.length, buf);
        final FosInt32 textLength = NettyFosTypeReader.fosInt32(buf);
        final FosInt32 reserve3 = NettyFosTypeReader.fosInt32(buf);

        verify(textLength.value().intValue() == buf.readableBytes(), "expected=%s, actual=%s", textLength, buf.readableBytes());

        ProtocolE p = ProtocolE.fromValue(protocolBytes);
        final FoscamOpCode foscamOpCode = FoscamOpCode.fromValue(p, operationCode);
        FoscamTextDto foscamTextDto = NettyFoscamTextParser.parse(foscamOpCode, buf);

        verify(0 == buf.readableBytes(), "expected=%s, actual=%s", 0, buf.readableBytes());

        return CommandDto.builder()
                .protocol(p)
                .operationCode(foscamOpCode)
                .reserve1(reserve1)
                .reserve2(reserve2)
                .textLength(textLength)
                .reserve3(reserve3)
                .text(foscamTextDto)
                .clientTimestamp(clientTimestamp)
                .build();
    }

}
