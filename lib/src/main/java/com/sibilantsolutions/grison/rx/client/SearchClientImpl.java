package com.sibilantsolutions.grison.rx.client;

import com.sibilantsolutions.grison.driver.foscam.entity.SearchReqTextEntity;
import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import com.sibilantsolutions.grison.rx.net.ChannelSender;
import io.reactivex.rxjava3.core.Flowable;

public class SearchClientImpl implements SearchClient {

    private final ChannelSender channelSender;

    public SearchClientImpl(ChannelSender channelSender) {
        this.channelSender = channelSender;
    }

    @Override
    public Flowable<ChannelSendEvent> search() {
        return channelSender.doSend(SearchReqTextEntity.builder().build());
    }

}
