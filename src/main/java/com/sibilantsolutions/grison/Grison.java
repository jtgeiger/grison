package com.sibilantsolutions.grison;

import java.io.IOException;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.demo.ApiDemo;
import com.sibilantsolutions.grison.demo.Demo;
import io.reactivex.rxjava3.exceptions.UndeliverableException;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

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

        rxInit();

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

    private static void rxInit() {
        RxJavaPlugins.setErrorHandler(e -> {
            boolean isUndeliverableException = false;
            if (e instanceof UndeliverableException) {
                isUndeliverableException = true;
                e = e.getCause();
            }
            if ((e instanceof IOException) || (e instanceof SocketException)) {
                // fine, irrelevant network problem or API that throws on cancellation
                return;
            }
            if (e instanceof InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                // that's likely a bug in the application
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
                return;
            }
            if (e instanceof IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
                return;
            }
            log.warn("Undeliverable exception received, not sure what to do (source was UE={})", isUndeliverableException, e);
        });
    }

}
