package com.sibilantsolutions.grison.driver.foscam.domain;

public class TalkDataText extends AbstractAudioDataText
{

    static public TalkDataText parse( String data )
    {
        TalkDataText text = new TalkDataText();

        text.parseImpl( data );

        return text;
    }

}
