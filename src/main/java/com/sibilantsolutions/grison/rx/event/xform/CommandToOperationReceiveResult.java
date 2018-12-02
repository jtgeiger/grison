package com.sibilantsolutions.grison.rx.event.xform;

import java.util.Arrays;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.driver.foscam.dto.AlarmNotifyTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.Unk02TextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.FoscamTextEntity;
import com.sibilantsolutions.grison.driver.foscam.mapper.DtoToEntity;
import com.sibilantsolutions.grison.rx.event.result.OperationReceiveResult;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class CommandToOperationReceiveResult implements FlowableTransformer<CommandDto, OperationReceiveResult> {

    @Override
    public Publisher<OperationReceiveResult> apply(Flowable<CommandDto> upstream) {
        return upstream
                .map(CommandDto::text)
                .compose(new TextToEntity())
                .map(OperationReceiveResult::create);
    }

    private static class TextToEntity implements FlowableTransformer<FoscamTextDto, FoscamTextEntity> {

        @Override
        public Publisher<FoscamTextEntity> apply(Flowable<FoscamTextDto> upstream) {
            return upstream.publish(foscamTextDtoFlowable -> Flowable.merge(Arrays.asList(
                    foscamTextDtoFlowable.ofType(LoginRespTextDto.class).map(DtoToEntity.loginRespTextEntity::apply),
                    foscamTextDtoFlowable.ofType(VerifyRespTextDto.class).map(DtoToEntity.verifyRespTextEntity::apply),
                    foscamTextDtoFlowable.ofType(Unk02TextDto.class).map(DtoToEntity.unk02TextEntity::apply),
                    foscamTextDtoFlowable.ofType(VideoStartRespTextDto.class).map(DtoToEntity.videoStartRespTextEntity::apply),
                    foscamTextDtoFlowable.ofType(AudioStartRespTextDto.class).map(DtoToEntity.audioStartRespTextEntity::apply),
                    foscamTextDtoFlowable.ofType(TalkStartRespTextDto.class).map(DtoToEntity.talkStartRespTextEntity::apply),
                    foscamTextDtoFlowable.ofType(AlarmNotifyTextDto.class).map(DtoToEntity.alarmNotifyTextEntity::apply)
            )));
        }
    }
}
