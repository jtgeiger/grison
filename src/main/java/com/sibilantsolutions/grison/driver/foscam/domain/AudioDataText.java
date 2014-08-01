package com.sibilantsolutions.grison.driver.foscam.domain;

public class AudioDataText extends AbstractAudioDataText
{

    static public AudioDataText parse( byte[] data, int offset, int length )
    {
        AudioDataText text = new AudioDataText();

        text.parseImpl( data, offset, length );

        return text;
    }

}
