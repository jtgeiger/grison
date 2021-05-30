package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.rx.client.OpClient;
import com.sibilantsolutions.grison.rx.client.OpClientImpl;
import com.sibilantsolutions.grison.rx.event.action.AudioStartAction;
import com.sibilantsolutions.grison.rx.event.result.AudioStartSendResult;
import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import com.sibilantsolutions.grison.rx.net.ChannelSender;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;

public class AudioStartActionToAudioStartSendResult implements FlowableTransformer<AudioStartAction, AudioStartSendResult> {
    @Override
    public Publisher<AudioStartSendResult> apply(Flowable<AudioStartAction> upstream) {
        final Flowable<OpClient> opClientFlowable = upstream
                .map(audioStartAction -> audioStartAction.channel)
                .map(ChannelSender::new)
                .map(OpClientImpl::new);

        return opClientFlowable
                .flatMap(opClient -> opClient
                        .audioStart()
                        .map(channelSendEvent -> {
                            if (channelSendEvent == ChannelSendEvent.IN_FLIGHT) {
                                return AudioStartSendResult.IN_FLIGHT;
                            } else if (channelSendEvent == ChannelSendEvent.SENT) {
                                return AudioStartSendResult.SENT;
                            } else {
                                return new AudioStartSendResult(new RuntimeException(channelSendEvent.failureCause));
                            }
                        })
                );
    }
}
