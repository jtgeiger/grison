package com.sibilantsolutions.grison.net.retrofit;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

public class FoscamInsecureAuthInterceptor implements Interceptor {

    private static final String USER_QUERY_PARAM = "user";
    private static final String PASSWORD_QUERY_PARAM = "pwd";
    final String user;
    final String password;

    public FoscamInsecureAuthInterceptor(String user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();
        //HACK TODO: This sends the credentials in plain text.  Look for a better way.
        final HttpUrl.Builder newUrl = request
                .url()
                .newBuilder()
                .addQueryParameter(USER_QUERY_PARAM, user)
                .addQueryParameter(PASSWORD_QUERY_PARAM, password);

        final Request.Builder newRequest = request
                .newBuilder()
                .url(newUrl.build());

        return chain.proceed(newRequest.build());
    }
}
