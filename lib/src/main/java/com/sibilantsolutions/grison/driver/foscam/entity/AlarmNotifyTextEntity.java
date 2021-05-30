package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.domain.AlarmTypeE;

@AutoValue
public abstract class AlarmNotifyTextEntity implements FoscamTextEntity {

    public abstract AlarmTypeE alarmType();

    public static AlarmNotifyTextEntity.Builder builder() {
        return new AutoValue_AlarmNotifyTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder alarmType(AlarmTypeE alarmType);

        public abstract AlarmNotifyTextEntity build();
    }
}
