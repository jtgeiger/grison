package com.sibilantsolutions.grison;

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
//                CgiService svc = new CgiService( new InetSocketAddress( args[0], Integer.parseInt( args[1] ) ), args[2], args[3] );
//                CgiService.exec( svc.getStatus() );

            }
        }, "main" ).run();
    }

}
