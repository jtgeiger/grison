package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.rx.event.action.AbstractAction;
import com.sibilantsolutions.grison.rx.event.ui.ConnectUiEvent;
import com.sibilantsolutions.grison.rx.event.ui.UiEvent;
import com.sibilantsolutions.grison.rx.event.ui.VideoEndUiEvent;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;

public class UiEventToAbstractAction implements FlowableTransformer<UiEvent, AbstractAction> {
    @Override
    public Publisher<AbstractAction> apply(Flowable<UiEvent> upstream) {
        return upstream
                .publish(uiEventFlowable -> Flowable.merge(
                        uiEventFlowable.ofType(ConnectUiEvent.class).compose(new ConnectUiEventToOperationConnectAction()),
                        uiEventFlowable.ofType(VideoEndUiEvent.class).compose(new VideoEndEventToVideoEndAction())
                ));
    }
}
