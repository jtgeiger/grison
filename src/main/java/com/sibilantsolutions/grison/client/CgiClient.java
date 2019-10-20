package com.sibilantsolutions.grison.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.net.retrofit.CgiRetrofitService;
import com.sibilantsolutions.grison.net.retrofit.FoscamInsecureAuthInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CgiClient {

    private static final Logger LOG = LoggerFactory.getLogger(CgiClient.class);

    private CgiClient() {
    }

    public static CgiRetrofitService cgiRetrofitService(Retrofit retrofit) {
        return retrofit.create(CgiRetrofitService.class);
    }

    public static Retrofit retrofit(String host, int port, String username, String password) {
        //TODO: Dedicated HTTP logger; use Markers; use Trace level.
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(LOG::debug);
// set your desired log level
        logging.level(HttpLoggingInterceptor.Level.BODY);

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

}
