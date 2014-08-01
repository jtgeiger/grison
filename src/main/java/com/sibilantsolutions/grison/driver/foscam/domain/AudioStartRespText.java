package com.sibilantsolutions.grison.driver.foscam.domain;

public class AudioStartRespText extends AbstractStartRespText
{

    public static AudioStartRespText parse( byte[] data, int offset, int length )
    {
        AudioStartRespText text = new AudioStartRespText();

        text.parseImpl( data, offset, length );

        return text;
    }

}
