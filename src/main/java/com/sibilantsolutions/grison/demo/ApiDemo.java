package com.sibilantsolutions.grison.demo;

import javax.annotation.PostConstruct;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.sibilantsolutions.grison.client.AudioVideoClient;
import com.sibilantsolutions.grison.client.SearchClient;
import com.sibilantsolutions.grison.net.retrofit.CgiRetrofitService;
import com.sibilantsolutions.grison.net.retrofit.HttpResult;
import com.sibilantsolutions.grison.net.retrofit.RetrofitResultToHttpResult;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Single;
import retrofit2.adapter.rxjava3.Result;

@Component
@ConditionalOnProperty(
        value = Demo.UI_ENABLED_PROPERTY,
        havingValue = "false"
)
public class ApiDemo {

    private static final Logger LOG = LoggerFactory.getLogger(ApiDemo.class);

    private final AudioVideoClient audioVideoClient;
    private final CgiRetrofitService cgiRetrofitService;

    public ApiDemo(AudioVideoClient audioVideoClient, CgiRetrofitService cgiRetrofitService) {
        this.audioVideoClient = audioVideoClient;
        this.cgiRetrofitService = cgiRetrofitService;
    }

    @PostConstruct
    public void go() {

        audioVideoClient.stream()
                .subscribe(new LogSubscriber<>());

        SearchClient.search(50_080)
                .subscribe(new LogSubscriber<>());

        cgi(cgiRetrofitService);
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
