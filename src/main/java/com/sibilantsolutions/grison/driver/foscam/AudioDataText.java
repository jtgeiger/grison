package com.sibilantsolutions.grison.driver.foscam;

public class AudioDataText extends AbstractAudioDataText
{

    static public AudioDataText parse( String data )
    {
        AudioDataText text = new AudioDataText();

        text.parseImpl( data );

        return text;
    }

}
