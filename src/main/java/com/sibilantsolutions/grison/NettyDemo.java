package com.sibilantsolutions.grison;

import java.net.InetSocketAddress;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.domain.SearchProtocolOpCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.SearchReqText;
import com.sibilantsolutions.grison.net.netty.SearchChannelInitializer;
import com.sibilantsolutions.grison.net.retrofit.CgiRetrofitService;
import com.sibilantsolutions.grison.net.retrofit.FoscamInsecureAuthInterceptor;
import com.sibilantsolutions.grison.net.retrofit.HttpResult;
import com.sibilantsolutions.grison.net.retrofit.RetrofitResultToHttpResult;
import com.sibilantsolutions.grison.rx.State;
import com.sibilantsolutions.grison.rx.event.action.AbstractAction;
import com.sibilantsolutions.grison.rx.event.result.AbstractResult;
import com.sibilantsolutions.grison.rx.event.result.AudioVideoReceiveResult;
import com.sibilantsolutions.grison.rx.event.result.OperationReceiveResult;
import com.sibilantsolutions.grison.rx.event.ui.ConnectUiEvent;
import com.sibilantsolutions.grison.rx.event.ui.UiEvent;
import com.sibilantsolutions.grison.rx.event.xform.AbstractActionToAbstractResult;
import com.sibilantsolutions.grison.rx.event.xform.CommandToAudioVideoReceiveResult;
import com.sibilantsolutions.grison.rx.event.xform.CommandToOperationReceiveResult;
import com.sibilantsolutions.grison.rx.event.xform.StateAndResultToStateBiFunction;
import com.sibilantsolutions.grison.rx.event.xform.StateToState;
import com.sibilantsolutions.grison.rx.event.xform.UiEventToAbstractAction;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
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

        go11(host, port, username, password);

//        search();
//        cgi(host, port, username, password);
    }

    private void go11(String host, int port, String username, String password) {

        Flowable<UiEvent> events = Flowable.just(new ConnectUiEvent(host, port));

        //TODO: Handle upstream socket disconnect.

        Flowable<AbstractAction> uiEventsToActions = events
                .compose(new UiEventToAbstractAction());

        final FlowableProcessor<AbstractAction> dynamicActions = PublishProcessor.<AbstractAction>create().toSerialized();

//        //HACK TODO: Funkiness some times; connect the av channel but we never fire the av login; worker hasn't fixed it yet.
//        final Scheduler.Worker worker = Schedulers.io().createWorker();

        Flowable<AbstractAction> actions = Flowable
                .merge(
                        uiEventsToActions,
                        dynamicActions);

        final PublishProcessor<Command> operationDatastream = PublishProcessor.create();
        final PublishProcessor<Command> audioVideoDatastream = PublishProcessor.create();

        final Flowable<OperationReceiveResult> operationReceiveResults = operationDatastream
                .compose(new CommandToOperationReceiveResult());

        final Flowable<AudioVideoReceiveResult> audioVideoReceiveResults = audioVideoDatastream
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

        states
                //TODO: Pretend UI disabled video, but only if it was validly connected, after some period of time.
//                .observeOn(Schedulers.io())
                .subscribe(new LogSubscriber<>());
    }

    private void search() {

        EventLoopGroup group = new NioEventLoopGroup();

        final Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new SearchChannelInitializer(group));

        try {
            final Channel channel = bootstrap
                    .bind(50_080)   //TODO Use 0 for a random port; but firewall needs to be open to receive response.
                    .sync()
                    .channel();

            LOG.info("{} got channel.", channel);

            Command c = new Command();
            c.setProtocol(ProtocolE.SEARCH_PROTOCOL);
            c.setOpCode(SearchProtocolOpCodeE.Search_Req);
            c.setCommandText(new SearchReqText());
//
            channel
                    .writeAndFlush(
                            new DatagramPacket(
                                    Unpooled.wrappedBuffer(c.toDatastream()),
                                    new InetSocketAddress(BROADCAST_ADDRESS, BROADCAST_PORT)))
                    .addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            LOG.info("{} Wrote the msg.", future.channel());
                        } else {
                            LOG.error("Trouble: ", future.cause());
                        }
                    });
        } catch (InterruptedException e) {
            throw new UnsupportedOperationException("TODO (CSB)", e);
        }
//        } finally {
//            LOG.info("{} Shutting down.", group);
//            group.shutdownGracefully();
//        }
    }

    private void cgi(String host, int port, String username, String password) {

        //TODO: Dedicated HTTP logger; use Markers; use Trace level.
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(LOG::debug);
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

        httpClient.addInterceptor(new FoscamInsecureAuthInterceptor(username, password));

// add logging as last interceptor
        httpClient.addInterceptor(logging);

        final Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(String.format("http://%s:%d", host, port))
                .client(httpClient.build())
                .build();

        final CgiRetrofitService cgiRetrofitService = retrofit.create(CgiRetrofitService.class);

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
