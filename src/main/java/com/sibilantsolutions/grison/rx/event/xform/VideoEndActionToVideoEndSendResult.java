package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.rx.client.OpClient;
import com.sibilantsolutions.grison.rx.client.OpClientImpl;
import com.sibilantsolutions.grison.rx.event.action.VideoEndAction;
import com.sibilantsolutions.grison.rx.event.result.VideoEndSendResult;
import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import com.sibilantsolutions.grison.rx.net.ChannelSender;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class VideoEndActionToVideoEndSendResult implements FlowableTransformer<VideoEndAction, VideoEndSendResult> {
    @Override
    public Publisher<VideoEndSendResult> apply(Flowable<VideoEndAction> upstream) {
        final Flowable<OpClient> opClientFlowable = upstream
                .map(videoEndAction -> videoEndAction.channel)
                .map(ChannelSender::new)
                .map(OpClientImpl::new);

        return opClientFlowable
                .flatMap(opClient -> opClient
                        .videoEnd()
                        .map(channelSendEvent -> {
                            if (channelSendEvent == ChannelSendEvent.IN_FLIGHT) {
                                return VideoEndSendResult.IN_FLIGHT;
                            } else if (channelSendEvent == ChannelSendEvent.SENT) {
                                return VideoEndSendResult.SENT;
                            } else {
                                return new VideoEndSendResult(new RuntimeException(channelSendEvent.failureCause));
                            }
                        })
                );
    }
}
