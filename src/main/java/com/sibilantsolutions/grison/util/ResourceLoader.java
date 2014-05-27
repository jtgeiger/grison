package com.sibilantsolutions.grison.util;

import java.io.IOException;
import java.io.InputStream;

abstract public class ResourceLoader
{

    private ResourceLoader() {} //Prevent instantiation.

    static public String loadResource( String path )
    {
        InputStream ins = ResourceLoader.class.getResourceAsStream( path );

        if ( ins == null )
            throw new IllegalArgumentException( "Resource not found: " + path );

        String data = readInputStream( ins );

        try
        {
            ins.close();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        return data;
    }

    public static String readInputStream( InputStream ins )
    {
        StringBuilder sBuf = new StringBuilder();

        int numRead;
        byte[] buf = new byte[1024];
        try
        {
            while ( ( numRead = ins.read( buf ) ) != -1 )
            {
                String s = new String( buf, 0, numRead, Convert.cs );
                sBuf.append( s );
            }
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }

        return sBuf.toString();
    }

}
