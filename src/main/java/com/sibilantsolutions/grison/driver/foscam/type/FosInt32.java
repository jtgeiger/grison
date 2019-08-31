package com.sibilantsolutions.grison.driver.foscam.type;

import com.google.auto.value.AutoValue;
import com.google.common.primitives.UnsignedInteger;

/**
 * A 32-bit unsigned value.  Valid values are 0 to 2^32 - 1 (4,294,967,295).
 *
 * On the wire, these values are encoded Little Endian.
 */
@AutoValue
public abstract class FosInt32 {

    public abstract UnsignedInteger value();

    /**
     * Convert the given argument to a long by unsigned conversion and use the result to create a FosInt32.
     *
     * @param value the value to convert to a FosInt32.
     *
     * @return A FosInt32.
     */
    public static FosInt32 create(int value) {
        return new AutoValue_FosInt32(UnsignedInteger.valueOf(Integer.toUnsignedLong(value)));
    }

}
