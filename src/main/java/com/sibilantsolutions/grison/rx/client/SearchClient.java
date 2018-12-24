package com.sibilantsolutions.grison.rx.client;

import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import io.reactivex.Flowable;

public interface SearchClient {

    Flowable<ChannelSendEvent> search();

}
