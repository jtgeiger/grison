package com.sibilantsolutions.grison.net.netty.codec.parse;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.primitives.UnsignedInteger;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import io.netty.buffer.ByteBuf;

public class NettyFosTypeReaderTest {

    @Test
    public void fosInt32() {
        final ByteBuf buf = new ResourceToByteBuf().apply("/netty/fosint32.bin");
        final FosInt32 fos = NettyFosTypeReader.fosInt32(buf);
        //Testing little-endian behavior (bytes in the file are 55 66 77 88).
        assertEquals(UnsignedInteger.valueOf(0x88776655L), fos.value());  //2289526357
        //Testing that this byte sequence is negative (2289526357 is bigger than Integer.MAX_VALUE).
        assertEquals(UnsignedInteger.valueOf(Integer.toUnsignedLong(-2005440939)), fos.value());
        //Testing that interpreting the value as unsigned gives the expected value.
        assertEquals(Integer.parseUnsignedInt("2289526357"), fos.value().intValue());
        assertEquals(Long.parseUnsignedLong("2289526357"), fos.value().longValue());
    }
}