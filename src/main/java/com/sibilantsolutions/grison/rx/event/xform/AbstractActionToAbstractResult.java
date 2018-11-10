package com.sibilantsolutions.grison.rx.event.xform;

import java.util.Arrays;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.rx.event.action.AbstractAction;
import com.sibilantsolutions.grison.rx.event.action.AudioVideoConnectAction;
import com.sibilantsolutions.grison.rx.event.action.AudioVideoLoginAction;
import com.sibilantsolutions.grison.rx.event.action.LoginAction;
import com.sibilantsolutions.grison.rx.event.action.OperationConnectAction;
import com.sibilantsolutions.grison.rx.event.action.VerifyAction;
import com.sibilantsolutions.grison.rx.event.action.VideoEndAction;
import com.sibilantsolutions.grison.rx.event.action.VideoStartAction;
import com.sibilantsolutions.grison.rx.event.result.AbstractResult;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.processors.PublishProcessor;

public class AbstractActionToAbstractResult implements FlowableTransformer<AbstractAction, AbstractResult> {

    private final Subscriber<Command> operationDatastream;
    private final Subscriber<Command> audioVideoDatastream;

    public AbstractActionToAbstractResult(PublishProcessor<Command> operationDatastream, PublishProcessor<Command> audioVideoDatastream) {
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
                        abstractActionFlowable.ofType(AudioVideoConnectAction.class).compose(new AudioVideoConnectActionToAudioVideoConnectResult(audioVideoDatastream)),
                        abstractActionFlowable.ofType(AudioVideoLoginAction.class).compose(new AudioVideoLoginActionToAudioVideoLoginSendResult())
                )));
    }
}
