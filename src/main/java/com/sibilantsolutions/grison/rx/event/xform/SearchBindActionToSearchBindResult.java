package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import com.sibilantsolutions.grison.NettyDemo;
import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.net.netty.BootstrapBinder;
import com.sibilantsolutions.grison.net.netty.SearchChannelInitializer;
import com.sibilantsolutions.grison.rx.ChannelConnectEvent;
import com.sibilantsolutions.grison.rx.event.action.SearchBindAction;
import com.sibilantsolutions.grison.rx.event.result.SearchBindResult;
import io.netty.bootstrap.Bootstrap;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class SearchBindActionToSearchBindResult implements FlowableTransformer<SearchBindAction, SearchBindResult> {

    private final Subscriber<CommandDto> searchDatastream;

    public SearchBindActionToSearchBindResult(Subscriber<CommandDto> searchDatastream) {
        this.searchDatastream = searchDatastream;
    }

    @Override
    public Publisher<SearchBindResult> apply(Flowable<SearchBindAction> upstream) {

        final Bootstrap bootstrap = NettyDemo.broadcastBootstrap(new SearchChannelInitializer(searchDatastream));

        final Flowable<ChannelConnectEvent> channelConnectEventFlowable = new BootstrapBinder().bind(bootstrap);

        return channelConnectEventFlowable
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
