package com.sibilantsolutions.grison.rx.client;

import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import io.reactivex.rxjava3.core.Flowable;

public interface SearchClient {

    Flowable<ChannelSendEvent> search();

}
