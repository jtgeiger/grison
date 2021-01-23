package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.net.netty.BootstrapBinder;
import com.sibilantsolutions.grison.rx.event.action.SearchBindAction;
import com.sibilantsolutions.grison.rx.event.result.SearchBindResult;
import com.sibilantsolutions.grison.rx.net.ChannelConnectEvent;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;

public class SearchBindActionToSearchBindResult implements FlowableTransformer<SearchBindAction, SearchBindResult> {

    @Override
    public Publisher<SearchBindResult> apply(Flowable<SearchBindAction> upstream) {

        return upstream
                .map(searchBindAction -> searchBindAction.bootstrap)
                .flatMap(bootstrap -> new BootstrapBinder().bind(bootstrap))
                .map(channelConnectEvent -> {
                    if (channelConnectEvent == ChannelConnectEvent.IN_FLIGHT) {
                        return SearchBindResult.IN_FLIGHT;
                    } else if (channelConnectEvent.channel != null) {
                        return SearchBindResult.success(channelConnectEvent.channel);
                    } else if (channelConnectEvent.failureCause != null) {
                        return SearchBindResult.fail(channelConnectEvent.failureCause);
                    }

                    throw new IllegalArgumentException("Unexpected cce");
                });
    }
}
