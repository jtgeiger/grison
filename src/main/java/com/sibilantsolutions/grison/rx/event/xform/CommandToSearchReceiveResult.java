package com.sibilantsolutions.grison.rx.event.xform;

import java.util.Collections;

import org.reactivestreams.Publisher;

import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.SearchRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.FoscamTextEntity;
import com.sibilantsolutions.grison.driver.foscam.mapper.DtoToEntity;
import com.sibilantsolutions.grison.rx.event.result.SearchReceiveResult;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class CommandToSearchReceiveResult implements FlowableTransformer<CommandDto, SearchReceiveResult> {

    @Override
    public Publisher<SearchReceiveResult> apply(Flowable<CommandDto> upstream) {
        return upstream
                .map(CommandDto::text)
                .compose(new TextToEntity())
                .map(SearchReceiveResult::create);
    }

    private static class TextToEntity implements FlowableTransformer<FoscamTextDto, FoscamTextEntity> {

        @Override
        public Publisher<FoscamTextEntity> apply(Flowable<FoscamTextDto> upstream) {
            return upstream.publish(foscamTextDtoFlowable -> Flowable.merge(Collections.singletonList(
                    foscamTextDtoFlowable.ofType(SearchRespTextDto.class).map(DtoToEntity.searchRespTextEntity::apply)
            )));
        }
    }
}
