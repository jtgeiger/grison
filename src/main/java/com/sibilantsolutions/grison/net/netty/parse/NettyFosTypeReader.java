package com.sibilantsolutions.grison.net.netty.parse;

import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16R;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32R;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;
import io.netty.buffer.ByteBuf;

public final class NettyFosTypeReader {

    private NettyFosTypeReader() {
    }

    public static FosInt8 fosInt8(ByteBuf buf) {
        return new FosInt8(buf.readByte());
    }

    public static FosInt16 fosInt16(ByteBuf buf) {
        return new FosInt16(buf.readShortLE());
    }

    public static FosInt16R fosInt16R(ByteBuf buf) {
        return new FosInt16R(buf.readShort());
    }

    public static FosInt32 fosInt32(ByteBuf buf) {
        return new FosInt32(buf.readIntLE());
    }

    public static FosInt32R fosInt32R(ByteBuf buf) {
        return new FosInt32R(buf.readInt());
    }

}
