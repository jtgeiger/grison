package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.charset.Charset;
import java.util.Arrays;

public enum ProtocolE
{
    OPERATION_PROTOCOL( Values.OPERATION_PROTOCOL ),
    AUDIO_VIDEO_PROTOCOL( Values.AUDIO_VIDEO_PROTOCOL ),
    SEARCH_PROTOCOL( Values.SEARCH_PROTOCOL ),
    ;

    private interface Values
    {
        Charset cs = Charset.forName("ISO-8859-1");

        byte[] OPERATION_PROTOCOL = "MO_O".getBytes(cs);
        byte[] AUDIO_VIDEO_PROTOCOL = "MO_V".getBytes(cs);
        byte[] SEARCH_PROTOCOL = "MO_I".getBytes(cs);
    }

    private byte[] value;

    ProtocolE(byte[] value)
    {
        this.value = value;
    }

    public static ProtocolE fromValue(byte[] value)
    {
        if (Arrays.equals(value, OPERATION_PROTOCOL.value))
            return OPERATION_PROTOCOL;

        if (Arrays.equals(value, AUDIO_VIDEO_PROTOCOL.value))
            return AUDIO_VIDEO_PROTOCOL;

        if (Arrays.equals(value, SEARCH_PROTOCOL.value))
            return SEARCH_PROTOCOL;

        throw new IllegalArgumentException("Unexpected value=" + Arrays.toString(value));
    }

    public byte[] getValue()
    {
        return value;
    }

}
