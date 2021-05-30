package com.sibilantsolutions.grison.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.entity.SearchRespTextEntity;
import com.sibilantsolutions.grison.net.netty.SearchChannelInitializer;
import com.sibilantsolutions.grison.rx.event.action.SearchAction;
import com.sibilantsolutions.grison.rx.event.action.SearchBindAction;
import com.sibilantsolutions.grison.rx.event.result.SearchReceiveResult;
import com.sibilantsolutions.grison.rx.event.result.SearchSendResult;
import com.sibilantsolutions.grison.rx.event.xform.CommandToSearchReceiveResult;
import com.sibilantsolutions.grison.rx.event.xform.SearchActionToSearchSendResult;
import com.sibilantsolutions.grison.rx.event.xform.SearchBindActionToSearchBindResult;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import io.reactivex.rxjava3.processors.PublishProcessor;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchClient {

    private static final Logger LOG = LoggerFactory.getLogger(SearchClient.class);

    private SearchClient() {
    }

    public static Flowable<ImmutableList<SearchRespTextEntity>> search() {

        //TODO: SearchBuilder.

        final FlowableProcessor<CommandDto> searchDatastream = PublishProcessor.<CommandDto>create().toSerialized();

        final Bootstrap bootstrap = datagramBroadcastBootstrap(new SearchChannelInitializer(searchDatastream));

        return search(bootstrap, searchDatastream);
    }

    public static Flowable<ImmutableList<SearchRespTextEntity>> search(int localPort) {

        //TODO: SearchBuilder.

        final FlowableProcessor<CommandDto> searchDatastream = PublishProcessor.<CommandDto>create().toSerialized();

        final Bootstrap bootstrap = datagramBroadcastBootstrap(new SearchChannelInitializer(searchDatastream))
                .localAddress(localPort);

        return search(bootstrap, searchDatastream);
    }

    public static Flowable<ImmutableList<SearchRespTextEntity>> search(Bootstrap bootstrap, Flowable<CommandDto> searchDatastream) {

        //TODO: Convert into a State that will indicate the bind/send results as well as the receive results.

        return Flowable
                .just(new SearchBindAction(bootstrap))

                .compose(new SearchBindActionToSearchBindResult())

                .flatMap(searchBindResult -> {
                    if (searchBindResult.channel != null) {
                        return Flowable.just(new SearchAction(searchBindResult.channel));
                    } else {
                        LOG.info("searchBindResult={}.", searchBindResult);
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
                        LOG.info("searchSendResult={}.", searchSendResult);

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

    /**
     * Returns a Bootstrap that will be initialized with the given handler, and which uses a NioDatagramChannel and
     * enables ChannelOption.SO_BROADCAST.
     * <p>
     * The channels will listen on a random, ephemeral port.  This means that if a firewall is being used, the
     * inbound port must allow UDP traffic but it can't be known in advance what port will be used.  In this case the
     * returned Bootstrap can be modified with the localAddress() method to hardcode a port and the firewall can be
     * opened in advance.
     *
     * @param handler The handler used to initialize the channel.
     * @return A new Bootstrap.
     */
    public static Bootstrap datagramBroadcastBootstrap(ChannelHandler handler) {
        EventLoopGroup group = new NioEventLoopGroup();
        return new Bootstrap()
                .group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .localAddress(0)   //0 means a random port; but firewall needs to be open to receive response.
                .handler(handler);
    }

}
