package com.sibilantsolutions.grison.driver.foscam.domain;


public class AudioStartRespText extends AbstractStartRespText
{

    public static AudioStartRespText parse( String data )
    {
        AudioStartRespText text = new AudioStartRespText();

        text.parseImpl( data );

        return text;
    }

}
