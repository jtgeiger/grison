package com.sibilantsolutions.grison.driver.foscam.dto;

import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

//TODO: AutoValue
public class CommandDto {

    final ProtocolE protocol;
    final FoscamOpCode operationCode;
    final FosInt8 reserve1;
    final byte[] reserve2;  //8
    final FosInt32 textLength;
    final FosInt32 reserve3;
    final FoscamTextDto text;

    public CommandDto(ProtocolE protocol, FoscamOpCode operationCode, FosInt8 reserve1, byte[] reserve2, FosInt32 textLength, FosInt32 reserve3, FoscamTextDto text) {
        this.protocol = protocol;
        this.operationCode = operationCode;
        this.reserve1 = reserve1;
        this.reserve2 = reserve2;
        this.textLength = textLength;
        this.reserve3 = reserve3;
        this.text = text;
    }
}
