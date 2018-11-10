package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.rx.event.result.OperationReceiveResult;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class CommandToOperationReceiveResult implements FlowableTransformer<Command, OperationReceiveResult> {
    @Override
    public Publisher<OperationReceiveResult> apply(Flowable<Command> upstream) {
        return upstream.map(OperationReceiveResult::new);
    }
}
