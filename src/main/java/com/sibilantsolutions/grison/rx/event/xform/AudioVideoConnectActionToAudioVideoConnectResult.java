package com.sibilantsolutions.grison.rx.event.xform;

import java.net.InetSocketAddress;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.net.netty.AudioVideoConnectionBootstrap;
import com.sibilantsolutions.grison.net.netty.BootstrapConnector;
import com.sibilantsolutions.grison.rx.ChannelConnectEvent;
import com.sibilantsolutions.grison.rx.ConnectionRequestEvent;
import com.sibilantsolutions.grison.rx.event.action.AudioVideoConnectAction;
import com.sibilantsolutions.grison.rx.event.result.AudioVideoConnectResult;
import io.netty.bootstrap.Bootstrap;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class AudioVideoConnectActionToAudioVideoConnectResult implements FlowableTransformer<AudioVideoConnectAction, AudioVideoConnectResult> {

    private final Subscriber<Command> audioVideoDatastream;

    public AudioVideoConnectActionToAudioVideoConnectResult(Subscriber<Command> audioVideoDatastream) {
        this.audioVideoDatastream = audioVideoDatastream;
    }

    @Override
    public Publisher<AudioVideoConnectResult> apply(Flowable<AudioVideoConnectAction> upstream) {
        final Flowable<ConnectionRequestEvent> connectionRequestEventFlowable = upstream
                .map(operationConnectAction -> new ConnectionRequestEvent(
                        InetSocketAddress.createUnresolved(operationConnectAction.host, operationConnectAction.port)));

        final Flowable<Bootstrap> bootstrapFlowable =
                connectionRequestEventFlowable
                        .flatMapSingle(connectionRequestEvent -> new AudioVideoConnectionBootstrap()
                                .bootstrap(connectionRequestEvent, audioVideoDatastream));

        final Flowable<ChannelConnectEvent> channelConnectEventFlowable = bootstrapFlowable
                .flatMap(new BootstrapConnector()::connect);

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
