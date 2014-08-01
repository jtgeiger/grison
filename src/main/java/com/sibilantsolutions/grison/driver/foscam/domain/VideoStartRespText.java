package com.sibilantsolutions.grison.driver.foscam.domain;

public class VideoStartRespText extends AbstractStartRespText
{

    public static VideoStartRespText parse( byte[] data, int offset, int length )
    {
        VideoStartRespText text = new VideoStartRespText();

        text.parseImpl( data, offset, length );

        return text;
    }

}
