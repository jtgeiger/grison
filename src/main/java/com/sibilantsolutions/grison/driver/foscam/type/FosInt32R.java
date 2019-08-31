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

    /**
     * Convert the given argument to a long by unsigned conversion and use the result to create a FosInt32R.
     *
     * @param value the value to convert to a FosInt32R.
     *
     * @return A FosInt32R.
     */
    public static FosInt32R create(int value) {
        return new AutoValue_FosInt32R(UnsignedInteger.valueOf(Integer.toUnsignedLong(value)));
    }

}
