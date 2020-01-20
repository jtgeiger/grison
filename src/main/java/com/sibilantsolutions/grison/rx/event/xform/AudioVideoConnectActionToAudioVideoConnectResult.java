package com.sibilantsolutions.grison.rx.event.xform;

import java.net.InetSocketAddress;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.net.netty.AudioVideoChannelInitializer;
import com.sibilantsolutions.grison.net.netty.BootstrapConnector;
import com.sibilantsolutions.grison.net.netty.NioSocketConnectionBootstrap;
import com.sibilantsolutions.grison.rx.event.action.AudioVideoConnectAction;
import com.sibilantsolutions.grison.rx.event.result.AudioVideoConnectResult;
import com.sibilantsolutions.grison.rx.net.ChannelConnectEvent;
import com.sibilantsolutions.grison.rx.net.ConnectionRequestEvent;
import io.netty.bootstrap.Bootstrap;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class AudioVideoConnectActionToAudioVideoConnectResult implements FlowableTransformer<AudioVideoConnectAction, AudioVideoConnectResult> {

    private final Subscriber<CommandDto> audioVideoDatastream;

    public AudioVideoConnectActionToAudioVideoConnectResult(Subscriber<CommandDto> audioVideoDatastream) {
        this.audioVideoDatastream = audioVideoDatastream;
    }

    @Override
    public Publisher<AudioVideoConnectResult> apply(Flowable<AudioVideoConnectAction> upstream) {
        final Flowable<ConnectionRequestEvent> connectionRequestEventFlowable = upstream
                .map(operationConnectAction -> ConnectionRequestEvent.create(
                        InetSocketAddress.createUnresolved(operationConnectAction.host, operationConnectAction.port),
                        new AudioVideoChannelInitializer(audioVideoDatastream)));

        final Flowable<Bootstrap> bootstrapFlowable =
                connectionRequestEventFlowable
                        .flatMapSingle(NioSocketConnectionBootstrap::bootstrap);

        final Flowable<ChannelConnectEvent> channelConnectEventFlowable = bootstrapFlowable
                .flatMap(BootstrapConnector::connect);

        return channelConnectEventFlowable
                .map(channelConnectEvent -> {
                    if (channelConnectEvent == ChannelConnectEvent.IN_FLIGHT) {
                        return AudioVideoConnectResult.IN_FLIGHT;
                    } else if (channelConnectEvent.channel != null) {
                        return AudioVideoConnectResult.success(channelConnectEvent.channel);
                    } else if (channelConnectEvent.failureCause != null) {
                        return AudioVideoConnectResult.fail(channelConnectEvent.failureCause);
                    }

                    throw new IllegalArgumentException("Unexpected cce");
                });
    }
}
