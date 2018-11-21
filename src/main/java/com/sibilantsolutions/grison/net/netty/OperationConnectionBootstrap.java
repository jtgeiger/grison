package com.sibilantsolutions.grison.net.netty;

import org.reactivestreams.Subscriber;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.rx.ConnectionRequestEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.reactivex.Single;

public class OperationConnectionBootstrap {

    public Single<Bootstrap> bootstrap(ConnectionRequestEvent ocre, Subscriber<CommandDto> operationDatastream) {
        return Single.fromCallable(() -> bootstrapImpl(ocre, operationDatastream));
    }

    private Bootstrap bootstrapImpl(ConnectionRequestEvent ocre, Subscriber<CommandDto> operationDatastream) {

        final Bootstrap bootstrap = new Bootstrap();
        final NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(ocre.socketAddress)
                .handler(new OperationChannelInitializer(operationDatastream, group))
        ;

        return bootstrap;
    }

}
