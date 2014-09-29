package com.sibilantsolutions.grison.driver.foscam.domain;


public class Unk02Text implements DatastreamI
{

    private byte[] data;    //TODO: Parse (this seems to always be 1152 null 0x00 bytes)

    public byte[] getData()
    {
        return data;
    }

    public void setData( byte[] data )
    {
        this.data = data;
    }

    public static Unk02Text parse( byte[] data, int offset, int length )
    {
        Unk02Text text = new Unk02Text();

        byte[] d = new byte[length];
        System.arraycopy( data, offset, d, 0, length );
        text.data = d;

        return text;
    }

    @Override
    public byte[] toDatastream()
    {
        return data;
    }

}
