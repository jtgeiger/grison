package com.sibilantsolutions.grison.net.retrofit;

import retrofit2.Response;

//Models a Retrofit HTTP Response (which could be successful 2xx/3xx or failure 4xx/5xx), or an exception if we
//could not communicate with the server.
//Similar to the Retrofit Result class but also allows us to model an inFlight state.
public class HttpResult<T> {

    final Response<T> response;
    final Throwable failureCause;

    private HttpResult(Response<T> response, Throwable failureCause) {
        this.response = response;
        this.failureCause = failureCause;
    }

    public static <T> HttpResult<T> response(Response<T> response) {
        return new HttpResult<>(response, null);
    }

    public static <T> HttpResult<T> fail(Throwable failureCause) {
        return new HttpResult<>(null, failureCause);
    }

    public static <T> HttpResult<T> inFlight() {
        return new HttpResult<>(null, null);
    }

    public boolean isDone() {
        return !(response == null && failureCause == null);
    }

    public boolean isSuccess() {
        return response != null;
    }

    @Override
    public String toString() {
        if (!isDone()) {
            return "HttpResult<IN_FLIGHT>";
        } else if (isSuccess()) {
            return "HttpResult<result=" + response.code() + ">";
        } else {
            return "HttpResult<failure=" + failureCause + ">";
        }
    }
}
