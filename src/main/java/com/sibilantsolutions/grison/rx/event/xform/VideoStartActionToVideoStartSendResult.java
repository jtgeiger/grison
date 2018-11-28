package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.rx.ChannelSendEvent;
import com.sibilantsolutions.grison.rx.ChannelSender;
import com.sibilantsolutions.grison.rx.OpClient;
import com.sibilantsolutions.grison.rx.OpClientImpl;
import com.sibilantsolutions.grison.rx.event.action.VideoStartAction;
import com.sibilantsolutions.grison.rx.event.result.VideoStartSendResult;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class VideoStartActionToVideoStartSendResult implements FlowableTransformer<VideoStartAction, VideoStartSendResult> {
    @Override
    public Publisher<VideoStartSendResult> apply(Flowable<VideoStartAction> upstream) {
        final Flowable<OpClient> opClientFlowable = upstream
                .map(videoStartAction -> videoStartAction.channel)
                .map(ChannelSender::new)
                .map(OpClientImpl::new);

        return opClientFlowable
                .flatMap(opClient -> opClient
                        .videoStart()
                        .map(channelSendEvent -> {
                            if (channelSendEvent == ChannelSendEvent.IN_FLIGHT) {
                                return VideoStartSendResult.IN_FLIGHT;
                            } else if (channelSendEvent == ChannelSendEvent.SENT) {
                                return VideoStartSendResult.SENT;
                            } else {
                                return new VideoStartSendResult(new RuntimeException(channelSendEvent.failureCause));
                            }
                        })
                );
    }
}
