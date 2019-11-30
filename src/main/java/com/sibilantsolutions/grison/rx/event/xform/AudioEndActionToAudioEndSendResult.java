package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.rx.client.OpClient;
import com.sibilantsolutions.grison.rx.client.OpClientImpl;
import com.sibilantsolutions.grison.rx.event.action.AudioEndAction;
import com.sibilantsolutions.grison.rx.event.result.AudioEndSendResult;
import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import com.sibilantsolutions.grison.rx.net.ChannelSender;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class AudioEndActionToAudioEndSendResult implements FlowableTransformer<AudioEndAction, AudioEndSendResult> {
    @Override
    public Publisher<AudioEndSendResult> apply(Flowable<AudioEndAction> upstream) {
        final Flowable<OpClient> opClientFlowable = upstream
                .map(audioEndAction -> audioEndAction.channel)
                .map(ChannelSender::new)
                .map(OpClientImpl::new);

        return opClientFlowable
                .flatMap(opClient -> opClient
                        .audioEnd()
                        .map(channelSendEvent -> {
                            if (channelSendEvent == ChannelSendEvent.IN_FLIGHT) {
                                return AudioEndSendResult.IN_FLIGHT;
                            } else if (channelSendEvent == ChannelSendEvent.SENT) {
                                return AudioEndSendResult.SENT;
                            } else {
                                return new AudioEndSendResult(new RuntimeException(channelSendEvent.failureCause));
                            }
                        })
                );
    }
}
