package com.sibilantsolutions.grison.driver.foscam.domain;

public class AudioDataText extends AbstractAudioDataText
{

    public AudioDataText()
    {
        super( 10 );    //Uptime field is represented in hundredths of a second for audio data.
    }

    static public AudioDataText parse( byte[] data, int offset, int length )
    {
        AudioDataText text = new AudioDataText();

        text.parseImpl( data, offset, length );

        return text;
    }

}
