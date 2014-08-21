package com.sibilantsolutions.grison;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.demo.Demo;
import com.sibilantsolutions.grison.util.Convert;
import com.sibilantsolutions.grison.util.ResourceReader;
import com.sibilantsolutions.iptools.util.DurationLoggingRunnable;
import com.sibilantsolutions.iptools.util.HexDumpDeferred;

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
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/check_user.cgi?user=" + args[2] + "&pwd=" + args[3] );
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/get_status.cgi?user=" + args[2] + "&pwd=" + args[3] );
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/get_camera_params.cgi?user=" + args[2] + "&pwd=" + args[3] );
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/get_params.cgi?user=" + args[2] + "&pwd=" + args[3] );
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/get_forbidden.cgi?user=" + args[2] + "&pwd=" + args[3] );
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/get_misc.cgi?user=" + args[2] + "&pwd=" + args[3] );
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/wifi_scan.cgi?user=" + args[2] + "&pwd=" + args[3] );
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/get_wifi_scan_result.cgi?user=" + args[2] + "&pwd=" + args[3] );
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/get_log.cgi?user=" + args[2] + "&pwd=" + args[3] );
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/get_log.cgi?user=" + args[2] + "&pwd=" + args[3] );
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/decoder_control.cgi?command=0&onestep=0&degree=500&user=" + args[2] + "&pwd=" + args[3] );
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/decoder_control.cgi?command=2&onestep=0&degree=500&user=" + args[2] + "&pwd=" + args[3] );
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/decoder_control.cgi?command=4&onestep=1&degree=5&user=" + args[2] + "&pwd=" + args[3] );
//                init( "http://" + args[0] + ":" + Integer.parseInt( args[1] ) + "/decoder_control.cgi?command=6&onestep=1&degree=50&user=" + args[2] + "&pwd=" + args[3] );
            }
        }, "main" ).run();
    }

    static public void init( String url )
    {
        URL u;

        try
        {
            u = new URL( url );
        }
        catch ( MalformedURLException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "MY TODO!", e );
        }

        log.info( "Opening connection to url=( {} ).", url );

        HttpURLConnection yukon;

        try
        {
            yukon = (HttpURLConnection)u.openConnection();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "MY TODO!", e );
        }

        int responseCode;

        try
        {
            responseCode = yukon.getResponseCode();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "MY TODO!", e );
        }

        if ( responseCode == HttpURLConnection.HTTP_OK )
        {
            Object content;

            try
            {
                content = yukon.getContent();
            }
            catch ( IOException e )
            {
                // TODO Auto-generated catch block
                throw new UnsupportedOperationException( "MY TODO!", e );
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
                throw new UnsupportedOperationException( "MY TODO!", e );
            }

            byte[] data = ResourceReader.readFullyAsBytes( ins );
            log.info( "Data: \n{}", HexDumpDeferred.prettyDump( data ) );
            String str = new String( data, 0, data.length, Convert.cs );
            log.info( "Data: \n{}", str );
        }
        else
        {
            log.error( "Unexepected response code={}.", responseCode );
        }

    }

}
