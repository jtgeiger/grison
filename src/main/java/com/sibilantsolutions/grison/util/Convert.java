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

    static public long toNum( String str )
    {
        long num = 0;

        for ( int i = 0; i < str.length(); i++ )
        {
            num <<= 8;

            char c1 = str.charAt( i );
            num += c1;
        }

        return num;
    }

    static public long toNumLittleEndian( String str )
    {
        long num = 0;

        for ( int i = str.length() - 1; i >= 0 ; i-- )
        {
            num <<= 8;

            char c1 = str.charAt( i );
            num += c1;
        }

        return num;
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
