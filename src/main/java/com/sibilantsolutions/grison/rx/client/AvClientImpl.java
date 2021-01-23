package com.sibilantsolutions.grison.rx.client;

import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqAudioVideoTextEntity;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import com.sibilantsolutions.grison.rx.net.ChannelSender;
import io.reactivex.rxjava3.core.Flowable;

public class AvClientImpl implements AvClient {

    private final ChannelSender channelSender;

    public AvClientImpl(ChannelSender channelSender) {
        this.channelSender = channelSender;
    }

    @Override
    public Flowable<ChannelSendEvent> audioVideoLogin(FosInt32 dataConnectionId) {

        return channelSender.doSend(LoginReqAudioVideoTextEntity.builder()
                .dataConnectionId(dataConnectionId)
                .build());
    }

}
