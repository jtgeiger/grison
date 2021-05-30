package com.sibilantsolutions.grison;

import java.io.IOException;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import io.reactivex.rxjava3.exceptions.UndeliverableException;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/**
 * Grisons, also known as South American wolverines, are mustelids native to Central and South America.
 * -- Wikipedia
 */
@SpringBootApplication
public class Grison {

    final static private Logger logger = LoggerFactory.getLogger(Grison.class);

    static public void main(final String[] args) {
        logger.info("main started.");
        rxInit();

        try {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(Grison.class);

            builder.headless(false);

            ConfigurableApplicationContext ctx = builder.run(args);
            logger.info("main finished: ctx={}.", ctx);
        } catch (Exception e) {
            logger.error("Exiting because initialization failed.");
            System.exit(9);
        }
    }

    private static void rxInit() {
        RxJavaPlugins.setErrorHandler(e -> {
            boolean isUndeliverableException = false;
            if (e instanceof UndeliverableException) {
                isUndeliverableException = true;
                e = e.getCause();
            }
            if (e instanceof SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return;
            }
            if (e instanceof IOException) {
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
            logger.warn("Undeliverable exception received, not sure what to do (source was UE={})", isUndeliverableException, e);
        });
    }

}
