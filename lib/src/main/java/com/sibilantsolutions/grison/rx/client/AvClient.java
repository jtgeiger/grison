package com.sibilantsolutions.grison.rx.client;

import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.rx.net.ChannelSendEvent;
import io.reactivex.rxjava3.core.Flowable;

public interface AvClient {

    Flowable<ChannelSendEvent> audioVideoLogin(FosInt32 dataConnectionId);

}
