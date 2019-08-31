package com.sibilantsolutions.grison.driver.foscam.type;

import com.google.auto.value.AutoValue;
import com.google.common.primitives.UnsignedBytes;
import com.google.common.primitives.UnsignedInteger;

/**
 * An 8-bit unsigned value.  Valid values are 0 to 255.
 */
@AutoValue
public abstract class FosInt8 {

    public static final FosInt8 ZERO = create((byte) 0);
    public static final FosInt8 ONE = create((byte) 1);

    public abstract UnsignedInteger value();

    public static FosInt8 create(byte value) {
        return new AutoValue_FosInt8(UnsignedInteger.valueOf(Byte.toUnsignedInt(value)));
    }

    public byte toByte() {
        return UnsignedBytes.checkedCast(value().intValue());
    }

}
