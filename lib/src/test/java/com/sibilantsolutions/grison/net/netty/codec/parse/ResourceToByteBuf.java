package com.sibilantsolutions.grison.net.netty.codec.parse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Function that reads a named resource fully into memory and returns it wrapped as an unpooled ByteBuf.
 */
public class ResourceToByteBuf implements Function<String, ByteBuf> {
    @Override
    public ByteBuf apply(String name) {
        final byte[] bytes;
        try {
            final Path path = Paths.get(getClass().getResource(name).toURI());
            bytes = Files.readAllBytes(path);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return Unpooled.wrappedBuffer(bytes);
    }
}
