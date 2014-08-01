package com.sibilantsolutions.grison.driver.foscam.domain;

public class TalkDataText extends AbstractAudioDataText
{

    static public TalkDataText parse( byte[] data, int offset, int length )
    {
        TalkDataText text = new TalkDataText();

        text.parseImpl( data, offset, length );

        return text;
    }

}
