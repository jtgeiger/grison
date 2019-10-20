package com.sibilantsolutions.grison;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.demo.ApiDemo;
import com.sibilantsolutions.grison.demo.Demo;

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

        int i = 0;
        final String host = args[i++];
        final int port = Integer.parseInt(args[i++]);
        final String username = args[i++];
        final String password = args[i++];

        if (i < args.length) {
            if (args[i].equals("ui")) {
                Demo.demo(host, port, username, password);
            } else {
                throw new IllegalArgumentException(args[i]);
            }
        } else {
            ApiDemo.go(host, port, username, password);
        }

        log.info("main() finished.");

    }

}
