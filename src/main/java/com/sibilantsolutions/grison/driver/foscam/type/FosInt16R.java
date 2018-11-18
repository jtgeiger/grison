package com.sibilantsolutions.grison.driver.foscam.type;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FosInt16R {

    public abstract short value();

    public static FosInt16R create(int value) {
        return new AutoValue_FosInt16R((short) value); //Keep bottom 16 bits.
    }

}
