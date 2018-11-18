package com.sibilantsolutions.grison.driver.foscam.type;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FosInt16 {

    public abstract short value();

    public static FosInt16 create(int value) {
        return new AutoValue_FosInt16((short) value); //Keep bottom 16 bits.
    }

}
