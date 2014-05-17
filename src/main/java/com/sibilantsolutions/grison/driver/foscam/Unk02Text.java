package com.sibilantsolutions.grison.driver.foscam;

public class Unk02Text implements DatastreamI
{

    private String data;    //TODO: Parse

    public String getData()
    {
        return data;
    }

    public void setData( String data )
    {
        this.data = data;
    }

    public static Unk02Text parse( String data )
    {
        Unk02Text text = new Unk02Text();

        text.data = data;

        return text;
    }

    @Override
    public String toDatastream()
    {
        return data;
    }

}
