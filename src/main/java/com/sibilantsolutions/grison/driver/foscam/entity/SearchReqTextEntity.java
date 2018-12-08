package com.sibilantsolutions.grison.driver.foscam.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SearchReqTextEntity implements FoscamTextEntity {

    public static SearchReqTextEntity.Builder builder() {
        return new AutoValue_SearchReqTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract SearchReqTextEntity build();
    }
}
