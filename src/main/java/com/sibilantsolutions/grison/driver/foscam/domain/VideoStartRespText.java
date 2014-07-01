package com.sibilantsolutions.grison.driver.foscam.domain;


public class VideoStartRespText extends AbstractStartRespText
{

    public static VideoStartRespText parse( String data )
    {
        VideoStartRespText text = new VideoStartRespText();

        text.parseImpl( data );

        return text;
    }

}
