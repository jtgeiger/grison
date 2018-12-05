package com.sibilantsolutions.grison.rx;

import io.reactivex.Flowable;

public interface OpClient {

    Flowable<ChannelSendEvent> login();

    Flowable<ChannelSendEvent> ping();

    Flowable<ChannelSendEvent> verify(String username, String password);

    Flowable<ChannelSendEvent> videoStart();

    Flowable<ChannelSendEvent> videoEnd();

    Flowable<ChannelSendEvent> audioStart();

}
