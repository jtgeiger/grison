package com.sibilantsolutions.grison.rx;

import static com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto.PASSWORD_LEN;
import static com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto.USER_LEN;

import java.nio.charset.StandardCharsets;

import com.google.common.base.Strings;
import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoEndTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartReqTextDto;
import io.reactivex.Flowable;

public class OpClientImpl implements OpClient {

    private final ChannelSender channelSender;

    public OpClientImpl(ChannelSender channelSender) {
        this.channelSender = channelSender;
    }

    @Override
    public Flowable<ChannelSendEvent> login() {

        final LoginReqOperationTextDto text = LoginReqOperationTextDto.builder().build();

        return channelSender.doSend(text);
    }

    @Override
    public Flowable<ChannelSendEvent> ping() {

        final KeepAliveOperationTextDto text = KeepAliveOperationTextDto.builder().build();

        return channelSender.doSend(text);
    }

    @Override
    public Flowable<ChannelSendEvent> verify(String username, String password) {

        final VerifyReqTextDto text = VerifyReqTextDto.builder()
                .user(Strings.padEnd(username, USER_LEN, (char) 0).getBytes(StandardCharsets.ISO_8859_1))
                .password(Strings.padEnd(password, PASSWORD_LEN, (char) 0).getBytes(StandardCharsets.ISO_8859_1))
                .build();

        return channelSender.doSend(text);
    }

    @Override
    public Flowable<ChannelSendEvent> videoStart() {

        final VideoStartReqTextDto text = VideoStartReqTextDto.builder().build();

        return channelSender.doSend(text);
    }

    @Override
    public Flowable<ChannelSendEvent> videoEnd() {

        final VideoEndTextDto text = VideoEndTextDto.builder().build();

        return channelSender.doSend(text);
    }

}
