package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.rx.event.action.VideoEndAction;
import com.sibilantsolutions.grison.rx.event.ui.VideoEndUiEvent;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class VideoEndEventToVideoEndAction implements FlowableTransformer<VideoEndUiEvent, VideoEndAction> {
    @Override
    public Publisher<VideoEndAction> apply(Flowable<VideoEndUiEvent> upstream) {
        return upstream.map(videoEndUiEvent -> {
            throw new UnsupportedOperationException("TODO");
        });
    }
}
