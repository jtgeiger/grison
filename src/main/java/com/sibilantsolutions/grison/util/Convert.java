package com.sibilantsolutions.grison.util;

import java.nio.charset.Charset;

public abstract class Convert
{

    final static public Charset cs = Charset.forName( "ISO-8859-1" );

    private Convert() {}    //Prevent instantiation.

    public static String toLittleEndian( long num )
    {
        StringBuilder buf = new StringBuilder();

        do
        {
            char cur = (char)( num & 0xFF );
            buf.append( cur );
            num >>= 8;

        } while ( num > 0 );

        return buf.toString();
    }

    static public String toLittleEndian( long num, int numBytes )
    {
        String val = toLittleEndian( num );

        if ( val.length() > numBytes )
            throw new IllegalArgumentException( "Overflow: num=" + num + ", numBytes=" + numBytes );

        return padRear( val, numBytes, (char)0 );
    }

    public static String padRear( String val, int numBytes, char padChar )
    {
        StringBuilder buf = new StringBuilder( val );

        for ( ; buf.length() < numBytes; )
        {
            buf.append( padChar );
        }

        return buf.toString();
    }

}
