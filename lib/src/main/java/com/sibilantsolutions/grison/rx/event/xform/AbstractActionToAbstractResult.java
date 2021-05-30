package com.sibilantsolutions.grison.rx.event.xform;

import java.util.Arrays;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.rx.event.action.AbstractAction;
import com.sibilantsolutions.grison.rx.event.action.AudioEndAction;
import com.sibilantsolutions.grison.rx.event.action.AudioStartAction;
import com.sibilantsolutions.grison.rx.event.action.AudioVideoConnectAction;
import com.sibilantsolutions.grison.rx.event.action.AudioVideoLoginAction;
import com.sibilantsolutions.grison.rx.event.action.LoginAction;
import com.sibilantsolutions.grison.rx.event.action.OperationConnectAction;
import com.sibilantsolutions.grison.rx.event.action.VerifyAction;
import com.sibilantsolutions.grison.rx.event.action.VideoEndAction;
import com.sibilantsolutions.grison.rx.event.action.VideoStartAction;
import com.sibilantsolutions.grison.rx.event.result.AbstractResult;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;

public class AbstractActionToAbstractResult implements FlowableTransformer<AbstractAction, AbstractResult> {

    private final Subscriber<CommandDto> operationDatastream;
    private final Subscriber<CommandDto> audioVideoDatastream;

    public AbstractActionToAbstractResult(Subscriber<CommandDto> operationDatastream, Subscriber<CommandDto> audioVideoDatastream) {
        this.operationDatastream = operationDatastream;
        this.audioVideoDatastream = audioVideoDatastream;
    }

    @Override
    public Publisher<AbstractResult> apply(Flowable<AbstractAction> upstream) {
        return upstream
                .publish(abstractActionFlowable -> Flowable.merge(Arrays.asList(
                        abstractActionFlowable.ofType(OperationConnectAction.class).compose(new OperationConnectActionToOperationConnectResult(operationDatastream)),
                        abstractActionFlowable.ofType(LoginAction.class).compose(new LoginActionToLoginSendResult()),
                        abstractActionFlowable.ofType(VerifyAction.class).compose(new VerifyActionToVerifySendResult()),
                        abstractActionFlowable.ofType(VideoStartAction.class).compose(new VideoStartActionToVideoStartSendResult()),
                        abstractActionFlowable.ofType(VideoEndAction.class).compose(new VideoEndActionToVideoEndSendResult()),
                        abstractActionFlowable.ofType(AudioStartAction.class).compose(new AudioStartActionToAudioStartSendResult()),
                        abstractActionFlowable.ofType(AudioEndAction.class).compose(new AudioEndActionToAudioEndSendResult()),
                        abstractActionFlowable.ofType(AudioVideoConnectAction.class).compose(new AudioVideoConnectActionToAudioVideoConnectResult(audioVideoDatastream)),
                        abstractActionFlowable.ofType(AudioVideoLoginAction.class).compose(new AudioVideoLoginActionToAudioVideoLoginSendResult())
                )));
    }
}
