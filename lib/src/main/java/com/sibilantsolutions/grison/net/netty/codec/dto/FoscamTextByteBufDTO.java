package com.sibilantsolutions.grison.net.netty.codec.dto;

import static io.netty.util.internal.ObjectUtil.checkPositiveOrZero;

import com.google.auto.value.AutoValue;
import com.google.common.primitives.UnsignedInteger;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamOpCode;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import io.netty.buffer.ByteBuf;

@AutoValue
public abstract class FoscamTextByteBufDTO {

    public abstract FoscamOpCode opCode();

    abstract ByteBuf autoTextBuf(); //Not public.

    public FosInt32 encodedLength() {
        return FosInt32.create(UnsignedInteger.valueOf(checkPositiveOrZero(autoTextBuf().readableBytes(), "readableBytes")));
    }

    public ByteBuf textBuf() {
        //Every invocation will get a duplicate that the caller can consume.
        //It's already been set as read-only.
        return autoTextBuf().duplicate();
    }

    public static FoscamTextByteBufDTO create(FoscamOpCode opCode, ByteBuf textBuf) {
        return new AutoValue_FoscamTextByteBufDTO(opCode, textBuf.asReadOnly());
    }
}
