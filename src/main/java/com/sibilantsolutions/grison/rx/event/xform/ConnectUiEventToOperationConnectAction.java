package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.rx.event.action.OperationConnectAction;
import com.sibilantsolutions.grison.rx.event.ui.ConnectUiEvent;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class ConnectUiEventToOperationConnectAction implements FlowableTransformer<ConnectUiEvent, OperationConnectAction> {
    @Override
    public Publisher<OperationConnectAction> apply(Flowable<ConnectUiEvent> upstream) {
        return upstream
                .map(connectUiEvent -> new OperationConnectAction(connectUiEvent.host, connectUiEvent.port));
    }
}
