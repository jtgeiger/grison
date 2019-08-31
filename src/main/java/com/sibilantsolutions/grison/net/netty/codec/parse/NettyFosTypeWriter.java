package com.sibilantsolutions.grison.net.netty.codec.parse;

import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16R;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32R;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;
import io.netty.buffer.ByteBuf;

public final class NettyFosTypeWriter {

    private NettyFosTypeWriter() {
    }

    public static ByteBuf write(FosInt8 val, ByteBuf buf) {
        return buf.writeByte(val.value());
    }

    public static ByteBuf write(FosInt16 val, ByteBuf buf) {
        return buf.writeShortLE(val.value());
    }

    public static ByteBuf write(FosInt16R val, ByteBuf buf) {
        return buf.writeShort(val.value());
    }

    public static ByteBuf write(FosInt32 val, ByteBuf buf) {
        return buf.writeIntLE(val.value().intValue());
    }

    public static ByteBuf write(FosInt32R val, ByteBuf buf) {
        return buf.writeInt(val.value().intValue());
    }

}
