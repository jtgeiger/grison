package com.sibilantsolutions.grison.driver.foscam.domain;


public class TalkStartRespText extends AbstractStartRespText
{

    public static TalkStartRespText parse( byte[] data, int offset, int length )
    {
        TalkStartRespText text = new TalkStartRespText();

        text.parseImpl( data, offset, length );

        return text;
    }

}
