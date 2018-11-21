package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.rx.event.result.OperationReceiveResult;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class CommandToOperationReceiveResult implements FlowableTransformer<CommandDto, OperationReceiveResult> {
    @Override
    public Publisher<OperationReceiveResult> apply(Flowable<CommandDto> upstream) {
        return upstream.map(OperationReceiveResult::new);
    }
}
