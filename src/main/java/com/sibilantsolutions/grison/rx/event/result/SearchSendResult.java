package com.sibilantsolutions.grison.rx.event.result;

public class SearchSendResult extends AbstractResult {

    public static final SearchSendResult IN_FLIGHT = new SearchSendResult(null);
    public static final SearchSendResult SENT = new SearchSendResult(null);

    public final Throwable failureCause;

    public SearchSendResult(Throwable failureCause) {
        this.failureCause = failureCause;
    }

}
