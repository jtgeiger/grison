package com.sibilantsolutions.grison.rx.client;

import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import io.reactivex.Flowable;

public interface OpClient {

    Flowable<ChannelSendEvent> login();

    Flowable<ChannelSendEvent> verify(String username, String password);

    Flowable<ChannelSendEvent> videoStart();

    Flowable<ChannelSendEvent> videoEnd();

    Flowable<ChannelSendEvent> audioStart();

    Flowable<ChannelSendEvent> audioEnd();

}
