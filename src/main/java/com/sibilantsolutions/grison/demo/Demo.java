package com.sibilantsolutions.grison.demo;

import java.net.InetSocketAddress;

import javax.swing.JLabel;

import com.sibilantsolutions.grison.driver.foscam.net.FoscamSession;
import com.sibilantsolutions.grison.evt.AlarmEvt;
import com.sibilantsolutions.grison.evt.AlarmHandlerI;
import com.sibilantsolutions.grison.evt.AudioHandlerI;
import com.sibilantsolutions.grison.evt.LostConnectionEvt;
import com.sibilantsolutions.grison.evt.LostConnectionHandlerI;
import com.sibilantsolutions.grison.ui.Ui;
import com.sibilantsolutions.iptools.util.DurationLoggingRunnable;

public class Demo
{
//    final static private Logger log = LoggerFactory.getLogger( Demo.class );

    static private AudioHandlerI audioHandler = new DemoAudioHandler();
    static private JLabel label = new JLabel();
    static private DemoImageHandler imageHandler = new DemoImageHandler();
    static private AlarmHandlerI alarmHandler = new AlarmHandlerI()
    {
        @Override
        public void onAlarm( AlarmEvt evt )
        {
            //No-op.  TODO.
        }
    };
    static
    {
        imageHandler.setLabel( label );
    }

    static public void demo( final String hostname, final int port, final String username, final String password )
    {
        Ui.buildUi( label );

        final LostConnectionHandlerI lostConnectionHandler = new LostConnectionHandlerI()
        {

            @Override
            public void onLostConnection( LostConnectionEvt evt )
            {
                connectLoopThread( hostname, port, username, password, this );
            }
        };

        connectLoopThread( hostname, port, username, password, lostConnectionHandler );

    }

    private static void connect( final String hostname, final int port, final String username,
            final String password, final LostConnectionHandlerI lostConnectionHandler )
    {

        FoscamSession session = FoscamSession.connect( new InetSocketAddress( hostname, port ),
                username, password, audioHandler, imageHandler, alarmHandler, lostConnectionHandler );

        session.audioStart();
//        session.talkStart();
        session.videoStart();
    }

    private static void connectLoop( final String hostname, final int port, final String username,
            final String password, final LostConnectionHandlerI lostConnectionHandler )
    {
        boolean connected = false;

        while ( ! connected )
        {
            try
            {
                connect( hostname, port, username, password, lostConnectionHandler );
                connected = true;
            }
            catch ( Exception e )
            {
                try
                {
                    Thread.sleep( 5 * 1000 );
                }
                catch ( InterruptedException ignored )
                {
                }
            }
        }
    }

    private static Thread connectLoopThread( final String hostname, final int port, final String username,
            final String password, final LostConnectionHandlerI lostConnectionHandler )
    {
        Runnable r = new Runnable()
        {

            @Override
            public void run()
            {
                connectLoop( hostname, port, username, password, lostConnectionHandler );
            }
        };

        r = new DurationLoggingRunnable( r, "session connector" );

        Thread t = new Thread( r, "session connector" );
        t.start();

        return t;
    }

}
