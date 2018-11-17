package com.sibilantsolutions.grison.driver.foscam.type;

public class FosInt8 {

    public final int value;

    public FosInt8(int value) {
        this.value = (value & 0xFF);
    }

}
