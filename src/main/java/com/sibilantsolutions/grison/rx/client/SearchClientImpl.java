package com.sibilantsolutions.grison.rx.client;

import com.sibilantsolutions.grison.driver.foscam.dto.SearchReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.SearchReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.mapper.EntityToDto;
import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import com.sibilantsolutions.grison.rx.net.ChannelSender;
import io.reactivex.Flowable;

public class SearchClientImpl implements SearchClient {

    private final ChannelSender channelSender;

    public SearchClientImpl(ChannelSender channelSender) {
        this.channelSender = channelSender;
    }

    @Override
    public Flowable<ChannelSendEvent> search() {
        final SearchReqTextDto searchReqTextDto = EntityToDto.searchReqTextDto.apply(SearchReqTextEntity.builder().build());

        return channelSender.doSend(searchReqTextDto);
    }

}
