package com.sibilantsolutions.grison.driver.foscam.type;

import com.google.auto.value.AutoValue;
import com.google.common.primitives.UnsignedInteger;

/**
 * A 32-bit unsigned value.  Valid values are 0 to 2^32 - 1 (4,294,967,295).
 *
 * On the wire, these values are encoded Big Endian.
 */
@AutoValue
public abstract class FosInt32R {

    public abstract UnsignedInteger value();

    public static FosInt32R create(UnsignedInteger value) {
        return new AutoValue_FosInt32R(value);
    }

}
