package com.sibilantsolutions.grison.rx;

import com.sibilantsolutions.grison.driver.foscam.dto.AudioStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoEndTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqOperationTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoEndTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.mapper.EntityToDto;
import io.reactivex.Flowable;

public class OpClientImpl implements OpClient {

    private final ChannelSender channelSender;

    public OpClientImpl(ChannelSender channelSender) {
        this.channelSender = channelSender;
    }

    @Override
    public Flowable<ChannelSendEvent> login() {

        final LoginReqOperationTextDto text = EntityToDto.loginReqOperationTextDto.apply(
                LoginReqOperationTextEntity.builder().build());

        return channelSender.doSend(text);
    }

    @Override
    public Flowable<ChannelSendEvent> verify(String username, String password) {

        final VerifyReqTextDto text = EntityToDto.verifyReqTextDto.apply(VerifyReqTextEntity.builder()
                .username(username)
                .password(password)
                .build());

        return channelSender.doSend(text);
    }

    @Override
    public Flowable<ChannelSendEvent> videoStart() {

        final VideoStartReqTextDto text = EntityToDto.videoStartReqTextDto.apply(VideoStartReqTextEntity
                .builder().build());

        return channelSender.doSend(text);
    }

    @Override
    public Flowable<ChannelSendEvent> videoEnd() {

        final VideoEndTextDto text = EntityToDto.videoEndTextDto.apply(VideoEndTextEntity.builder().build());

        return channelSender.doSend(text);
    }

    @Override
    public Flowable<ChannelSendEvent> audioStart() {
        final AudioStartReqTextDto text = EntityToDto.audioStartReqTextDto.apply(AudioStartReqTextEntity.builder().build());

        return channelSender.doSend(text);
    }

}
