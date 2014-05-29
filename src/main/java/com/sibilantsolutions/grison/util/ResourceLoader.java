package com.sibilantsolutions.grison.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

abstract public class ResourceLoader
{

    static private enum LoadMode
    {
        STRING,
        BYTES,
        ;
    }

    private ResourceLoader() {} //Prevent instantiation.

    static public byte[] loadResourceAsBytes( String path )
    {
        return (byte[])loadResourceImpl( path, LoadMode.BYTES );
    }

    static public String loadResourceAsString( String path )
    {
        return (String)loadResourceImpl( path, LoadMode.STRING );
    }

    static private Object loadResourceImpl( String path, LoadMode loadMode )
    {
        InputStream ins = ResourceLoader.class.getResourceAsStream( path );

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

}
