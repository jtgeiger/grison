package com.sibilantsolutions.grison.rx.event.xform;

import java.net.InetSocketAddress;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.net.netty.BootstrapConnector;
import com.sibilantsolutions.grison.net.netty.NioSocketConnectionBootstrap;
import com.sibilantsolutions.grison.net.netty.OperationChannelInitializer;
import com.sibilantsolutions.grison.rx.event.action.OperationConnectAction;
import com.sibilantsolutions.grison.rx.event.result.OperationConnectResult;
import com.sibilantsolutions.grison.rx.net.ChannelConnectEvent;
import com.sibilantsolutions.grison.rx.net.ConnectionRequestEvent;
import io.netty.bootstrap.Bootstrap;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;

public class OperationConnectActionToOperationConnectResult implements FlowableTransformer<OperationConnectAction, OperationConnectResult> {

    private final Subscriber<CommandDto> operationDatastream;

    public OperationConnectActionToOperationConnectResult(Subscriber<CommandDto> operationDatastream) {
        this.operationDatastream = operationDatastream;
    }

    @Override
    public Publisher<OperationConnectResult> apply(Flowable<OperationConnectAction> upstream) {
        final Flowable<ConnectionRequestEvent> connectionRequestEventFlowable = upstream
                .map(operationConnectAction -> ConnectionRequestEvent.create(
                        InetSocketAddress.createUnresolved(operationConnectAction.host, operationConnectAction.port),
                        new OperationChannelInitializer(operationDatastream)));

        final Flowable<Bootstrap> bootstrapFlowable =
                connectionRequestEventFlowable
                        .flatMapSingle(NioSocketConnectionBootstrap::bootstrap);

        final Flowable<ChannelConnectEvent> channelConnectEventFlowable = bootstrapFlowable
                .flatMap(BootstrapConnector::connect);

        return channelConnectEventFlowable
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
