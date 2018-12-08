package com.sibilantsolutions.grison;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.entity.SearchRespTextEntity;
import com.sibilantsolutions.grison.net.retrofit.CgiRetrofitService;
import com.sibilantsolutions.grison.net.retrofit.FoscamInsecureAuthInterceptor;
import com.sibilantsolutions.grison.net.retrofit.HttpResult;
import com.sibilantsolutions.grison.net.retrofit.RetrofitResultToHttpResult;
import com.sibilantsolutions.grison.rx.State;
import com.sibilantsolutions.grison.rx.event.action.AbstractAction;
import com.sibilantsolutions.grison.rx.event.action.SearchAction;
import com.sibilantsolutions.grison.rx.event.action.SearchBindAction;
import com.sibilantsolutions.grison.rx.event.result.AbstractResult;
import com.sibilantsolutions.grison.rx.event.result.AudioVideoReceiveResult;
import com.sibilantsolutions.grison.rx.event.result.OperationReceiveResult;
import com.sibilantsolutions.grison.rx.event.result.SearchReceiveResult;
import com.sibilantsolutions.grison.rx.event.result.SearchSendResult;
import com.sibilantsolutions.grison.rx.event.ui.ConnectUiEvent;
import com.sibilantsolutions.grison.rx.event.ui.UiEvent;
import com.sibilantsolutions.grison.rx.event.xform.AbstractActionToAbstractResult;
import com.sibilantsolutions.grison.rx.event.xform.CommandToAudioVideoReceiveResult;
import com.sibilantsolutions.grison.rx.event.xform.CommandToOperationReceiveResult;
import com.sibilantsolutions.grison.rx.event.xform.CommandToSearchReceiveResult;
import com.sibilantsolutions.grison.rx.event.xform.SearchActionToSearchSendResult;
import com.sibilantsolutions.grison.rx.event.xform.SearchBindActionToSearchBindResult;
import com.sibilantsolutions.grison.rx.event.xform.StateAndResultToStateBiFunction;
import com.sibilantsolutions.grison.rx.event.xform.StateToState;
import com.sibilantsolutions.grison.rx.event.xform.UiEventToAbstractAction;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NettyDemo {

    private static final Logger LOG = LoggerFactory.getLogger(NettyDemo.class);

    private static final String BROADCAST_ADDRESS = "255.255.255.255";
    private static final int BROADCAST_PORT = 10_000;

    void go(String host, int port, String username, String password) {

        go11(host, port, username, password)
                .subscribe(new LogSubscriber<>());

//        search()
//                .subscribe(new LogSubscriber<>());
//        cgi(cgiRetrofitService(retrofit(host, port, username, password)));
    }

    public static Flowable<State> go11(String host, int port, String username, String password) {

        Flowable<UiEvent> events = Flowable.just(new ConnectUiEvent(host, port));

        //TODO: Handle upstream socket disconnect.

        Flowable<AbstractAction> uiEventsToActions = events
                .compose(new UiEventToAbstractAction());

        final FlowableProcessor<AbstractAction> dynamicActions = PublishProcessor.<AbstractAction>create().toSerialized();

        Flowable<AbstractAction> actions = Flowable
                .merge(
                        uiEventsToActions,
                        dynamicActions);

        final FlowableProcessor<CommandDto> operationDatastream = PublishProcessor.<CommandDto>create().toSerialized();
        final FlowableProcessor<CommandDto> audioVideoDatastream = PublishProcessor.<CommandDto>create().toSerialized();

        final Flowable<OperationReceiveResult> operationReceiveResults = operationDatastream
                .observeOn(Schedulers.io())
                .compose(new CommandToOperationReceiveResult());

        final Flowable<AudioVideoReceiveResult> audioVideoReceiveResults = audioVideoDatastream
                .observeOn(Schedulers.io())
                .compose(new CommandToAudioVideoReceiveResult());

        final Flowable<AbstractResult> actionResults = actions
                .compose(new AbstractActionToAbstractResult(operationDatastream, audioVideoDatastream));

        Flowable<AbstractResult> results = Flowable.merge(
                actionResults,
                operationReceiveResults,
                audioVideoReceiveResults);

        Flowable<State> states = results
                .scanWith(
                        () -> State.INITIAL,
                        new StateAndResultToStateBiFunction());

        states = states
                .compose(new StateToState(dynamicActions, username, password));

//        states
//                //TODO: Pretend UI disabled video, but only if it was validly connected, after some period of time.
////                .observeOn(Schedulers.io())
//                .subscribe(new LogSubscriber<>());

        return states;
    }

    public static Flowable<ImmutableList<SearchRespTextEntity>> search() {

        final FlowableProcessor<CommandDto> searchDatastream = PublishProcessor.<CommandDto>create().toSerialized();

        //TODO: Convert into a State that will indicate the bind/send results as well as the receive results.

        return Flowable
                .just(new SearchBindAction())

                .compose(new SearchBindActionToSearchBindResult(searchDatastream))

                .flatMap(searchBindResult -> {
                    if (searchBindResult.channel != null) {
                        return Flowable.just(new SearchAction(searchBindResult.channel));
                    } else {
                        return Flowable.empty();    //TODO: Can't ignore inflight/fail results.
                    }
                })

                .compose(new SearchActionToSearchSendResult())

                .flatMap(searchSendResult -> {
                    if (searchSendResult == SearchSendResult.SENT) {
                        return searchDatastream
                                .observeOn(Schedulers.io())
                                .compose(new CommandToSearchReceiveResult());
                    } else {
                        return Flowable.empty();    //TODO: Can't ignore inflight/fail results.
                    }
                })

                .map(SearchReceiveResult::text)

                .ofType(SearchRespTextEntity.class)

                .scan(ImmutableList.of(),
                        (searchResultState, searchRespTextEntity) -> new ImmutableList.Builder<SearchRespTextEntity>()
                                .addAll(searchResultState)
                                .add(searchRespTextEntity)
                                .build());
    }

    public static Bootstrap broadcastBootstrap(ChannelHandler handler) {
        EventLoopGroup group = new NioEventLoopGroup();
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .localAddress(50_080)   //TODO Use 0 for a random port; but firewall needs to be open to receive response.
                .handler(handler);
        return bootstrap;
    }

    private void cgi(CgiRetrofitService cgiRetrofitService) {

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
                .startWith(HttpResult.inFlight());

        httpResultFlowable
                .subscribe(new LogSubscriber<>());

    }

    public static CgiRetrofitService cgiRetrofitService(Retrofit retrofit) {
        return retrofit.create(CgiRetrofitService.class);
    }

    public static Retrofit retrofit(String host, int port, String username, String password) {
        //TODO: Dedicated HTTP logger; use Markers; use Trace level.
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(LOG::debug);
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

        httpClient.addInterceptor(new FoscamInsecureAuthInterceptor(username, password));

// add logging as last interceptor
        httpClient.addInterceptor(logging);

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(String.format("http://%s:%d", host, port))
                .client(httpClient.build())
                .build();
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
