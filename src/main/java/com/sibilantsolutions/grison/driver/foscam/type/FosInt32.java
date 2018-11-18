package com.sibilantsolutions.grison.driver.foscam.type;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FosInt32 {

    public abstract int value();

    public static FosInt32 create(int value) {
        return new AutoValue_FosInt32(value);
    }

}
