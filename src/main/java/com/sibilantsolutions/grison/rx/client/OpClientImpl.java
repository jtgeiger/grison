package com.sibilantsolutions.grison.rx.client;

import com.sibilantsolutions.grison.driver.foscam.entity.AudioEndTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqOperationTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoEndTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoStartReqTextEntity;
import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import com.sibilantsolutions.grison.rx.net.ChannelSender;
import io.reactivex.Flowable;

public class OpClientImpl implements OpClient {

    private final ChannelSender channelSender;

    public OpClientImpl(ChannelSender channelSender) {
        this.channelSender = channelSender;
    }

    @Override
    public Flowable<ChannelSendEvent> login() {

        return channelSender.doSend(LoginReqOperationTextEntity.builder().build());
    }

    @Override
    public Flowable<ChannelSendEvent> verify(String username, String password) {

        return channelSender.doSend(VerifyReqTextEntity.builder()
                .username(username)
                .password(password)
                .build());
    }

    @Override
    public Flowable<ChannelSendEvent> videoStart() {

        return channelSender.doSend(VideoStartReqTextEntity
                .builder().build());
    }

    @Override
    public Flowable<ChannelSendEvent> videoEnd() {

        return channelSender.doSend(VideoEndTextEntity.builder().build());
    }

    @Override
    public Flowable<ChannelSendEvent> audioStart() {

        return channelSender.doSend(AudioStartReqTextEntity.builder().build());
    }

    @Override
    public Flowable<ChannelSendEvent> audioEnd() {
        return channelSender.doSend(AudioEndTextEntity.builder().build());
    }

}
