package com.sibilantsolutions.grison.driver.foscam.type;

public class FosInt16R {

    public final short value;

    public FosInt16R(int value) {
        this.value = (short) value;  //Keep bottom 16 bits.
    }

}
