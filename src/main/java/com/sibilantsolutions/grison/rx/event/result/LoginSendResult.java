package com.sibilantsolutions.grison.rx.event.result;

public class LoginSendResult extends AbstractResult {

    public static final LoginSendResult IN_FLIGHT = new LoginSendResult(null);
    public static final LoginSendResult SENT = new LoginSendResult(null);

    public final Throwable failureCause;

    public LoginSendResult(Throwable failureCause) {
        this.failureCause = failureCause;
    }

    @Override
    public String toString() {
        if (this == IN_FLIGHT) {
            return "LoginSendResult{IN_FLIGHT}";
        }
        return "LoginSendResult{" +
                "failureCause=" + failureCause +
                '}';
    }
}
