package com.sibilantsolutions.grison.net.netty;

import org.reactivestreams.Subscriber;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.rx.net.ConnectionRequestEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.reactivex.Single;

public class NioSocketConnectionBootstrap {

    public Single<Bootstrap> bootstrap(ConnectionRequestEvent ocre, Subscriber<CommandDto> audioVideoDatastream, ChannelHandler channelHandler) {
        return Single.fromCallable(() -> bootstrapImpl(ocre, audioVideoDatastream, channelHandler));
    }

    private Bootstrap bootstrapImpl(ConnectionRequestEvent ocre, Subscriber<CommandDto> audioVideoDatastream, ChannelHandler channelHandler) {

        final Bootstrap bootstrap = new Bootstrap();
        final NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(ocre.socketAddress)
                .handler(channelHandler)
        ;

        return bootstrap;
    }

}
