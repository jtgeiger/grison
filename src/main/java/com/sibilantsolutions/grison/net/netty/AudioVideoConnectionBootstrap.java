package com.sibilantsolutions.grison.net.netty;

import org.reactivestreams.Subscriber;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.rx.ConnectionRequestEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.reactivex.Single;

public class AudioVideoConnectionBootstrap {

    public Single<Bootstrap> bootstrap(ConnectionRequestEvent ocre, Subscriber<Command> audioVideoDatastream) {
        return Single.fromCallable(() -> bootstrapImpl(ocre, audioVideoDatastream));
    }

    private Bootstrap bootstrapImpl(ConnectionRequestEvent ocre, Subscriber<Command> audioVideoDatastream) {

        final Bootstrap bootstrap = new Bootstrap();
        final NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(ocre.socketAddress)
                .handler(new AudioVideoChannelInitializer(audioVideoDatastream, group))
        ;

        return bootstrap;
    }

}
