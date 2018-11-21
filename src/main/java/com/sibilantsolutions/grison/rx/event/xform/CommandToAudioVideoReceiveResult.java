package com.sibilantsolutions.grison.rx.event.xform;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.rx.event.result.AudioVideoReceiveResult;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class CommandToAudioVideoReceiveResult implements FlowableTransformer<CommandDto, AudioVideoReceiveResult> {
    @Override
    public Publisher<AudioVideoReceiveResult> apply(Flowable<CommandDto> upstream) {
        return upstream.map(AudioVideoReceiveResult::new);
    }
}
