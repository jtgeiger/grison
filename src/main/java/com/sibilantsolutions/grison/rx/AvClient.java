package com.sibilantsolutions.grison.rx;

import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import io.reactivex.Completable;

public interface AvClient {

    Completable audioVideoLogin(FosInt32 dataConnectionId);

}
