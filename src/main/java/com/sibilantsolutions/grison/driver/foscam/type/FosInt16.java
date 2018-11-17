package com.sibilantsolutions.grison.driver.foscam.type;

public class FosInt16 {

    public final short value;

    public FosInt16(int value) {
        this.value = (short) value;  //Keep bottom 16 bits.
    }

}
