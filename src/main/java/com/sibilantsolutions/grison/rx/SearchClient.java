package com.sibilantsolutions.grison.rx;

import io.reactivex.Flowable;

public interface SearchClient {

    Flowable<ChannelSendEvent> search();

}
