package com.sibilantsolutions.grison.driver.foscam.domain;

public class TalkDataText extends AbstractAudioDataText
{

    public TalkDataText()
    {
        super( 1 ); //Uptime field is represented in milliseconds for talk data.
    }

    static public TalkDataText parse( byte[] data, int offset, int length )
    {
        TalkDataText text = new TalkDataText();

        text.parseImpl( data, offset, length );

        return text;
    }

}
