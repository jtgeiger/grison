package com.sibilantsolutions.grison;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.demo.Demo;
import com.sibilantsolutions.iptools.util.DurationLoggingRunnable;

/**
 * Grisons, also known as South American wolverines, are mustelids native to Central and South America.
 *   -- Wikipedia
 */
public class Grison
{

    final static private Logger log = LoggerFactory.getLogger( Grison.class );

    static public void main( final String[] args )
    {
        log.info( "main() started." );

        new DurationLoggingRunnable( new Runnable() {

            @Override
            public void run()
            {
                Demo.demo( args[0], Integer.parseInt( args[1] ), args[2], args[3] );
            }
        }, "main" ).run();
    }

    @SuppressWarnings( "unused" )
    private void init( String url )
    {
        URL u;

        try
        {
            u = new URL( url );
        }
        catch ( MalformedURLException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        URLConnection yukon;

        try
        {
            yukon = u.openConnection();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        Object content;

        try
        {
            content = yukon.getContent();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        log.info( "Headers={}.", yukon.getHeaderFields() );
        log.info( "Content type={}, content={}.", yukon.getContentType(), content );

        InputStream ins;

        try
        {
            ins = yukon.getInputStream();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        try
        {
            byte[] buf = new byte[2048];
            int numRead;

            while ( ( numRead = ins.read( buf ) ) != -1 )
            {
                for ( int i = 0; i < numRead; i++ )
                {
                    char c = (char)buf[i];
                    System.out.print( c );
                }
            }
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

    }

}
