package com.sibilantsolutions.grison.demo;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.client.AudioVideoClient;
import com.sibilantsolutions.grison.client.CgiClient;
import com.sibilantsolutions.grison.client.SearchClient;
import com.sibilantsolutions.grison.net.retrofit.CgiRetrofitService;
import com.sibilantsolutions.grison.net.retrofit.HttpResult;
import com.sibilantsolutions.grison.net.retrofit.RetrofitResultToHttpResult;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.adapter.rxjava3.Result;

public class ApiDemo {

    private static final Logger LOG = LoggerFactory.getLogger(ApiDemo.class);

    private ApiDemo() {
    }

    public static void go(String host, int port, String username, String password) {

        AudioVideoClient.stream(host, port, username, password)
                .subscribe(new LogSubscriber<>());

        SearchClient.search(50_080)
                .subscribe(new LogSubscriber<>());

        cgi(CgiClient.cgiRetrofitService(CgiClient.retrofit(host, port, username, password)));
    }

    private static void cgi(CgiRetrofitService cgiRetrofitService) {

//        final Flowable<Response<String>> resultSingle = cgiRetrofitService.checkUser(username, password);
//        final Single<Result<String>> resultSingle = cgiRetrofitService.irOff();
//        final Single<Result<String>> resultSingle = cgiRetrofitService.irAuto();
//        final Single<Result<String>> resultSingle = cgiRetrofitService.up();
//        final Single<Result<String>> resultSingle = cgiRetrofitService.up(80);
//        final Single<Result<String>> resultSingle = cgiRetrofitService.upOneStep();
//        final Single<Result<String>> resultSingle = cgiRetrofitService.down();
//        final Single<Result<String>> resultSingle = cgiRetrofitService.down(150);
//        final Single<Result<String>> resultSingle = cgiRetrofitService.left(80);
//        final Single<Result<String>> resultSingle = cgiRetrofitService.right();
        final Single<Result<String>> resultSingle = cgiRetrofitService.center();
//        final Single<Result<String>> resultSingle = cgiRetrofitService.goToPreset1();
//        final Single<Result<String>> resultSingle = cgiRetrofitService.goToPreset2();

//        final Completable ans = cgiRetrofitService.irOff(username, password);
//                .toFlowable();
//        final Flowable<Response<String>> resultSingle = cgiRetrofitService.checkUserOmitAuth();

        final Single<HttpResult<String>> result = resultSingle
                .compose(new RetrofitResultToHttpResult<>());

        final Flowable<HttpResult<String>> httpResultFlowable = result
                .toFlowable()
                .startWithItem(HttpResult.inFlight());

        httpResultFlowable
                .subscribe(new LogSubscriber<>());

    }

    private static class LogSingleObserver<T> implements SingleObserver<T> {

        @Override
        public void onSubscribe(Disposable d) {
            LOG.info("onSubscribe disposable={}.", d);
        }

        @Override
        public void onSuccess(T t) {
            LOG.info("onSuccess item={}.", t);
        }

        @Override
        public void onError(Throwable e) {
            LOG.error("onError: ", new RuntimeException(e));
        }
    }

    private static class LogObserver<T> implements Observer<T> {

        @Override
        public void onSubscribe(Disposable d) {
            LOG.info("onSubscribe disposable={}.", d);
        }

        @Override
        public void onNext(T t) {
            LOG.info("onNext item={}.", t);
        }

        @Override
        public void onError(Throwable e) {
            LOG.error("onError: ", new RuntimeException(e));
        }

        @Override
        public void onComplete() {
            LOG.info("onComplete.");
        }
    }

    private static class LogSubscriber<T> implements FlowableSubscriber<T> {

        private Subscription s;

        @Override
        public void onSubscribe(Subscription s) {
            LOG.info("onSubscribe subscription={}.", s);
            this.s = s;
            this.s.request(60);
        }

        @Override
        public void onNext(T t) {
            LOG.info("onNext item={}.", t);
            s.request(60);
        }

        @Override
        public void onError(Throwable t) {
            LOG.error("onError:", new RuntimeException(t));
        }

        @Override
        public void onComplete() {
            LOG.info("onComplete.");
        }
    }

}
