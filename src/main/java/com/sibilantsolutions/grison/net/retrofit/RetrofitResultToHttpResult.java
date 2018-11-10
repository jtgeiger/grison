package com.sibilantsolutions.grison.net.retrofit;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import retrofit2.adapter.rxjava2.Result;

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
