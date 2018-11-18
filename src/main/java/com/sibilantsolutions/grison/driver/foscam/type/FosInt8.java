package com.sibilantsolutions.grison.driver.foscam.type;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FosInt8 {

    public abstract int value();

    public static FosInt8 create(int value) {
        return new AutoValue_FosInt8(value & 0xFF); //Keep bottom 8 bits.
    }

}
