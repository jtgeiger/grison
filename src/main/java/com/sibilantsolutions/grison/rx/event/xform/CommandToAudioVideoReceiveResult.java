package com.sibilantsolutions.grison.rx.event.xform;

import java.util.Arrays;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.driver.foscam.dto.AudioDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.FoscamTextEntity;
import com.sibilantsolutions.grison.driver.foscam.mapper.DtoToEntity;
import com.sibilantsolutions.grison.rx.event.result.AudioVideoReceiveResult;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;

public class CommandToAudioVideoReceiveResult implements FlowableTransformer<CommandDto, AudioVideoReceiveResult> {

    @Override
    public Publisher<AudioVideoReceiveResult> apply(Flowable<CommandDto> upstream) {
        return upstream
                .map(CommandDto::text)
                .compose(new TextToEntity())
                .map(AudioVideoReceiveResult::create);
    }

    private static class TextToEntity implements FlowableTransformer<FoscamTextDto, FoscamTextEntity> {

        @Override
        public Publisher<FoscamTextEntity> apply(Flowable<FoscamTextDto> upstream) {
            return upstream.publish(foscamTextDtoFlowable -> Flowable.merge(Arrays.asList(
                    foscamTextDtoFlowable.ofType(VideoDataTextDto.class).map(DtoToEntity.videoDataTextEntity::apply),
                    foscamTextDtoFlowable.ofType(AudioDataTextDto.class).map(DtoToEntity.audioDataTextEntity::apply),
                    foscamTextDtoFlowable.ofType(TalkDataTextDto.class).map(DtoToEntity.talkDataTextEntity::apply)
            )));
        }
    }
}
