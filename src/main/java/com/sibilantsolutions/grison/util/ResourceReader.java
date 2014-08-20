package com.sibilantsolutions.grison.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

abstract public class ResourceReader
{

    static private enum ReadMode
    {
        STRING,
        BYTES,
        ;
    }

    private ResourceReader() {} //Prevent instantiation.

    public static ByteArrayOutputStream readFullyAsByteArrayOutputStream( InputStream ins )
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream( 1024 );

        byte[] buf = new byte[1024];

        try
        {
            for ( int numRead; ( numRead = ins.read( buf ) ) != -1; )
            {
                baos.write( buf, 0, numRead );
            }
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }

        return baos;
    }

    public static byte[] readFullyAsBytes( InputStream ins )
    {
        ByteArrayOutputStream baos = readFullyAsByteArrayOutputStream( ins );

        return baos.toByteArray();
    }

    public static String readFullyAsString( InputStream ins )
    {
        ByteArrayOutputStream baos = readFullyAsByteArrayOutputStream( ins );

        try
        {
            return baos.toString( Convert.cs.name() );
        }
        catch ( UnsupportedEncodingException e )
        {
            throw new RuntimeException( e );
        }
    }

    static public byte[] readResourceAsBytes( String path )
    {
        return (byte[])readResourceImpl( path, ReadMode.BYTES );
    }

    static public String readResourceAsString( String path )
    {
        return (String)readResourceImpl( path, ReadMode.STRING );
    }

    static private Object readResourceImpl( String path, ReadMode loadMode )
    {
        InputStream ins = ResourceReader.class.getResourceAsStream( path );

        if ( ins == null )
            throw new IllegalArgumentException( "Resource not found: " + path );

        Object data;

        switch ( loadMode )
        {
            case STRING:
                data = readFullyAsString( ins );
                break;

            case BYTES:
                data = readFullyAsBytes( ins );
                break;

            default:
                throw new IllegalArgumentException( "Unexpected mode=" + loadMode );
        }

        try
        {
            ins.close();
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }

        return data;
    }

}
