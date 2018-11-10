package com.sibilantsolutions.grison.rx;

import io.reactivex.Completable;

public interface AvClient {

    Completable audioVideoLogin(byte[] dataConnectionId);

}
