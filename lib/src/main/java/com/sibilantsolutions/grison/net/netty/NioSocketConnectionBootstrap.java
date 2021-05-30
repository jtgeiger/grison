package com.sibilantsolutions.grison.net.netty;

import com.sibilantsolutions.grison.rx.net.ConnectionRequestEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.reactivex.rxjava3.core.Single;

public class NioSocketConnectionBootstrap {

    private NioSocketConnectionBootstrap() {
        throw new IllegalStateException("No instances.");
    }

    public static Single<Bootstrap> bootstrap(ConnectionRequestEvent connectionRequestEvent) {
        return Single.fromCallable(() -> bootstrapImpl(connectionRequestEvent));
    }

    private static Bootstrap bootstrapImpl(ConnectionRequestEvent connectionRequestEvent) {

        final Bootstrap bootstrap = new Bootstrap();
        final NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(connectionRequestEvent.socketAddress())
                .handler(connectionRequestEvent.channelHandler())
        ;

        return bootstrap;
    }

}
