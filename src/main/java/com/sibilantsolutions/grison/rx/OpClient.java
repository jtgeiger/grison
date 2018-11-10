package com.sibilantsolutions.grison.rx;

import io.reactivex.Completable;

public interface OpClient {

    Completable login();

    Completable ping();

    Completable verify(String username, String password);

    Completable videoStart();

    Completable videoEnd();

}
