package com.sibilantsolutions.grison.net.netty.codec.parse;

import com.google.common.primitives.UnsignedInteger;
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
        return FosInt8.create(buf.readByte());
    }

    public static FosInt16 fosInt16(ByteBuf buf) {
        return FosInt16.create(buf.readShortLE());
    }

    public static FosInt16R fosInt16R(ByteBuf buf) {
        return FosInt16R.create(buf.readShort());
    }

    public static FosInt32 fosInt32(ByteBuf buf) {
        return FosInt32.create(UnsignedInteger.fromIntBits(buf.readIntLE()));
    }

    public static FosInt32R fosInt32R(ByteBuf buf) {
        return FosInt32R.create(UnsignedInteger.fromIntBits(buf.readInt()));
    }

}
