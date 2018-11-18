package com.sibilantsolutions.grison.net.netty.parse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ResourceToByteBuf implements Function<String, ByteBuf> {
    @Override
    public ByteBuf apply(String name) {
        try {
            final Path path = Paths.get(getClass().getResource(name).toURI());
            final byte[] bytes = Files.readAllBytes(path);
            return Unpooled.wrappedBuffer(bytes);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
