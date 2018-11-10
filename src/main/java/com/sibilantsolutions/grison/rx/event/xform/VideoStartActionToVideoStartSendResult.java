package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

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
                        .<VideoStartSendResult>toFlowable()
                        .startWith(Flowable.just(VideoStartSendResult.IN_FLIGHT))
                        .onErrorReturn(VideoStartSendResult::new)
                );
    }
}
