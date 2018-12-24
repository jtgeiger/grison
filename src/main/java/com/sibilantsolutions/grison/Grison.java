package com.sibilantsolutions.grison;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        new NettyDemo().go(args[0], Integer.parseInt(args[1]), args[2], args[3]);
//        Demo.demo(args[0], Integer.parseInt(args[1]), args[2], args[3]);

        log.info("main() finished.");

    }

}
