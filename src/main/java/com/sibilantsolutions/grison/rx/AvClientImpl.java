package com.sibilantsolutions.grison.rx;

import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqAudioVideoTextDto;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import io.reactivex.Flowable;

public class AvClientImpl implements AvClient {

    private final ChannelSender channelSender;

    public AvClientImpl(ChannelSender channelSender) {
        this.channelSender = channelSender;
    }

    @Override
    public Flowable<ChannelSendEvent> audioVideoLogin(FosInt32 dataConnectionId) {

        final LoginReqAudioVideoTextDto text = LoginReqAudioVideoTextDto.builder()
                .dataConnectionId(dataConnectionId)
                .build();

        return channelSender.doSend(text);
    }

}
