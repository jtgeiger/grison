package com.sibilantsolutions.grison.driver.foscam.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.DecoderControlE;
import com.sibilantsolutions.grison.util.Convert;
import com.sibilantsolutions.grison.util.ResourceReader;
import com.sibilantsolutions.iptools.util.HexDumpDeferred;

public class CgiService
{
    final static private Logger log = LoggerFactory.getLogger( CgiService.class );

    private InetSocketAddress address;
    private String username;
    private String password;

    public CgiService( InetSocketAddress address, String username, String password )
    {
        this.address = address;
        this.username = username;
        this.password = password;
    }

    static private void addEncoded( String str, StringBuilder buf )
    {
        try
        {
            str = URLEncoder.encode( str, Convert.cs.name() );
        }
        catch ( UnsupportedEncodingException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "MY TODO!", e );
        }

        buf.append( str );
    }

    public String decoderControl( DecoderControlE command, int onestep, int degree )
    {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

        map.put( "command", "" + command.getValue() );
        map.put( "onestep", "" + onestep );
        map.put( "degree", "" + degree );

        return url( "decoder_control.cgi", map );
    }

    static public byte[] exec( String url )
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

            return data;
        }
        else
        {
            log.error( "Unexpected response code={}.", responseCode );
            throw new UnsupportedOperationException( "MY TODO!" );
        }
    }

    public String getStatus()
    {
        return url( "get_status.cgi" );
    }

    private String url( String cgiCommand )
    {
        return url( cgiCommand, new LinkedHashMap<String, String>() );
    }

    private String url( String cgiCommand, LinkedHashMap<String, String> paramNamesToValues )
    {
        paramNamesToValues.put( "user", username );
        paramNamesToValues.put( "pwd", password );

        return url( cgiCommand, address, paramNamesToValues );
    }

    static private String url( String cgiCommand, InetSocketAddress address, LinkedHashMap<String, String> paramNamesToValues )
    {
        StringBuilder buf = new StringBuilder();
        buf.append( "http://" );
        buf.append( address.getHostName() );
        buf.append( ':' );
        buf.append( address.getPort() );
        buf.append( '/' );
        buf.append( cgiCommand );
        buf.append( '?' );

        int count = 0;

        for ( Iterator<Entry<String, String>> iterator = paramNamesToValues.entrySet().iterator(); iterator.hasNext(); count++ )
        {
            Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();

            if ( count > 0 )
                buf.append( '&' );

            buf.append( key );
            buf.append( '=' );
            addEncoded( value, buf );
        }

        return buf.toString();
    }

}

