package com.sibilantsolutions.grison.rx.event.result;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.entity.FoscamTextEntity;

@AutoValue
public abstract class OperationReceiveResult extends AbstractResult {

    public abstract FoscamTextEntity text();

    public static OperationReceiveResult create(FoscamTextEntity text) {
        return new AutoValue_OperationReceiveResult(text);
    }
}
