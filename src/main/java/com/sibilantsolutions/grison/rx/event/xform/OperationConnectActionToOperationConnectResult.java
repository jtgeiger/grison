package com.sibilantsolutions.grison.rx.event.xform;

import java.net.InetSocketAddress;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.net.netty.BootstrapConnector;
import com.sibilantsolutions.grison.net.netty.OperationConnectionBootstrap;
import com.sibilantsolutions.grison.rx.ChannelConnectEvent;
import com.sibilantsolutions.grison.rx.ConnectionRequestEvent;
import com.sibilantsolutions.grison.rx.event.action.OperationConnectAction;
import com.sibilantsolutions.grison.rx.event.result.OperationConnectResult;
import io.netty.bootstrap.Bootstrap;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class OperationConnectActionToOperationConnectResult implements FlowableTransformer<OperationConnectAction, OperationConnectResult> {

    private final Subscriber<CommandDto> operationDatastream;

    public OperationConnectActionToOperationConnectResult(Subscriber<CommandDto> operationDatastream) {
        this.operationDatastream = operationDatastream;
    }

    @Override
    public Publisher<OperationConnectResult> apply(Flowable<OperationConnectAction> upstream) {
        final Flowable<ConnectionRequestEvent> connectionRequestEventFlowable = upstream
                .map(operationConnectAction -> new ConnectionRequestEvent(
                        InetSocketAddress.createUnresolved(operationConnectAction.host, operationConnectAction.port)));
//                            operationConnectAction.username, operationConnectAction.password));

        final Flowable<Bootstrap> bootstrapFlowable =
                connectionRequestEventFlowable
                        .flatMapSingle(connectionRequestEvent -> new OperationConnectionBootstrap()
                                .bootstrap(connectionRequestEvent, operationDatastream));

        final Flowable<ChannelConnectEvent> channelConnectEventFlowable = bootstrapFlowable
//                    .doOnNext(bootstrap -> LOG.info("onNext bootstrap={}", bootstrap))
                .flatMap(new BootstrapConnector()::connect);

        return channelConnectEventFlowable
//                    .doOnNext(channelConnectEvent -> LOG.info("onNext channelConnectEvent={}", channelConnectEvent))
                .map(channelConnectEvent -> {
                    if (channelConnectEvent == ChannelConnectEvent.IN_FLIGHT) {
                        return OperationConnectResult.IN_FLIGHT;
                    } else if (channelConnectEvent.channel != null) {
                        return OperationConnectResult.success(channelConnectEvent.channel);
                    } else if (channelConnectEvent.failureCause != null) {
                        return OperationConnectResult.fail(channelConnectEvent.failureCause);
                    }

                    throw new IllegalArgumentException("Unexpected cce");
                });
    }
}
