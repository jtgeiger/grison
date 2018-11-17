package com.sibilantsolutions.grison.net.netty.parse;

import io.netty.buffer.ByteBuf;

public final class NettyByteBufHelper {

    private NettyByteBufHelper() {
    }

    /**
     * Read the given number of bytes from the ByteBuf into an array and return the array.
     * <p>
     * Since this copies bytes out of the buffer it should be used sparingly for relatively small lengths.
     *
     * @param length number of bytes to read from the buffer
     * @param buf    buffer to read from
     * @return a new byte array of the given length
     */
    public static byte[] readBytes(int length, ByteBuf buf) {
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        return bytes;
    }

}
