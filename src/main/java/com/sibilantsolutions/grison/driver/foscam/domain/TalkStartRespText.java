package com.sibilantsolutions.grison.driver.foscam.domain;


public class TalkStartRespText extends AbstractStartRespText
{

    public static TalkStartRespText parse( String data )
    {
        TalkStartRespText text = new TalkStartRespText();

        text.parseImpl( data );

        return text;
    }

}
