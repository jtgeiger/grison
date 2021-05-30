package com.sibilantsolutions.grison.driver.foscam.type;

import com.google.auto.value.AutoValue;
import com.google.common.primitives.UnsignedInteger;

/**
 * A 16-bit unsigned value.  Valid values are 0 to 65535.
 *
 * On the wire, these values are encoded Big Endian.
 */
@AutoValue
public abstract class FosInt16R {

    public abstract UnsignedInteger value();

    public static FosInt16R create(short value) {
        return new AutoValue_FosInt16R(UnsignedInteger.valueOf(Short.toUnsignedInt(value)));
    }

    public short toShort() {
        return Integer.valueOf(value().intValue()).shortValue();
    }

}
