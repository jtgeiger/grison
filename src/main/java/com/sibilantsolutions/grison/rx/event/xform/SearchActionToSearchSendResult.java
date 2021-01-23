package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.rx.client.SearchClient;
import com.sibilantsolutions.grison.rx.client.SearchClientImpl;
import com.sibilantsolutions.grison.rx.event.action.SearchAction;
import com.sibilantsolutions.grison.rx.event.result.SearchSendResult;
import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import com.sibilantsolutions.grison.rx.net.ChannelSender;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;

public class SearchActionToSearchSendResult implements FlowableTransformer<SearchAction, SearchSendResult> {
    @Override
    public Publisher<SearchSendResult> apply(Flowable<SearchAction> upstream) {
        final Flowable<SearchClient> opClientFlowable = upstream
                .map(searchAction -> searchAction.channel)
                .map(ChannelSender::new)
                .map(SearchClientImpl::new);

        return opClientFlowable
                .flatMap(searchClient -> searchClient
                        .search()
                        .map(channelSendEvent -> {
                            if (channelSendEvent == ChannelSendEvent.IN_FLIGHT) {
                                return SearchSendResult.IN_FLIGHT;
                            } else if (channelSendEvent == ChannelSendEvent.SENT) {
                                return SearchSendResult.SENT;
                            } else {
                                return new SearchSendResult(new RuntimeException(channelSendEvent.failureCause));
                            }
                        })
                );
    }
}
