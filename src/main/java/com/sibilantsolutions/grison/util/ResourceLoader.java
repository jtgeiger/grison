package com.sibilantsolutions.grison.util;

import java.io.IOException;
import java.io.InputStream;

abstract public class ResourceLoader
{

    private ResourceLoader() {} //Prevent instantiation.

    static public String loadResource( String path )
    {
        StringBuilder sBuf = new StringBuilder();
        InputStream ins = ResourceLoader.class.getResourceAsStream( path );

        if ( ins == null )
            throw new IllegalArgumentException( "Resource not found: " + path );

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
