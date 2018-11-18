package com.sibilantsolutions.grison.driver.foscam.type;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FosInt32R {

    public abstract int value();

    public static FosInt32R create(int value) {
        return new AutoValue_FosInt32R(value);
    }

}
