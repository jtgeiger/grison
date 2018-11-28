package com.sibilantsolutions.grison.rx;

import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import io.reactivex.Flowable;

public interface AvClient {

    Flowable<ChannelSendEvent> audioVideoLogin(FosInt32 dataConnectionId);

}
