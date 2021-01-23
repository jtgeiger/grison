package com.sibilantsolutions.grison.net.retrofit;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.core.SingleTransformer;
import retrofit2.adapter.rxjava3.Result;

//Convert the Retrofit Result to our own HttpResult.
public class RetrofitResultToHttpResult<T> implements SingleTransformer<Result<T>, HttpResult<T>> {
    @Override
    public SingleSource<HttpResult<T>> apply(Single<Result<T>> upstream) {
        return upstream
                .map(result -> {
                    if (result.isError()) {
                        return HttpResult.fail(result.error());
                    } else {
                        return HttpResult.response(result.response());
                    }
                });
    }
}
