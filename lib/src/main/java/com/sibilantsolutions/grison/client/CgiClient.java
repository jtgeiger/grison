package com.sibilantsolutions.grison.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.sibilantsolutions.grison.net.retrofit.CgiRetrofitService;
import com.sibilantsolutions.grison.net.retrofit.FoscamInsecureAuthInterceptor;
import com.sibilantsolutions.grison.net.retrofit.SetTimeDtoConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Component
@EnableConfigurationProperties(CameraConnectionProperties.class)
public class CgiClient {

    private static final Logger LOG = LoggerFactory.getLogger(CgiClient.class);

    private CgiClient() {
    }

    @Bean
    public static CgiRetrofitService cgiRetrofitService(Retrofit retrofit) {
        return retrofit.create(CgiRetrofitService.class);
    }

    @Bean
    public static Retrofit retrofit(CameraConnectionProperties cameraConnectionProperties) {
        //TODO: Dedicated HTTP logger; use Markers; use Trace level.
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(LOG::debug);
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

        httpClient.addInterceptor(new FoscamInsecureAuthInterceptor(cameraConnectionProperties.getUsername(), cameraConnectionProperties.getPassword()));

// add logging as last interceptor
        httpClient.addInterceptor(logging);

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(new SetTimeDtoConverterFactory())
                .baseUrl(String.format("http://%s:%d", cameraConnectionProperties.getHost(), cameraConnectionProperties.getPort()))
                .client(httpClient.build())
                .build();
    }

}
