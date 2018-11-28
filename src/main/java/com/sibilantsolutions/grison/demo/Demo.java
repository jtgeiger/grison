package com.sibilantsolutions.grison.demo;

import java.net.InetSocketAddress;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.NettyDemo;
import com.sibilantsolutions.grison.driver.foscam.net.FoscamSession;
import com.sibilantsolutions.grison.evt.AlarmEvt;
import com.sibilantsolutions.grison.evt.AlarmHandlerI;
import com.sibilantsolutions.grison.evt.AudioHandlerI;
import com.sibilantsolutions.grison.evt.LostConnectionHandlerI;
import com.sibilantsolutions.grison.rx.State;
import com.sibilantsolutions.grison.ui.Ui;
import com.sibilantsolutions.utils.util.DurationLoggingRunnable;
import io.reactivex.Flowable;

public class Demo
{
    final static private Logger LOG = LoggerFactory.getLogger(Demo.class);

    static private AudioHandlerI audioHandler = new DemoAudioHandler();
    static private JLabel imageLabel = new JLabel();
    static private JLabel uptimeLabel = new JLabel();
    static private JLabel timestampLabel = new JLabel();
    static private JLabel fpsLabel = new JLabel();
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
        imageHandler.setImageLabel(imageLabel);
        imageHandler.setUptimeLabel(uptimeLabel);
        imageHandler.setTimestampLabel(timestampLabel);
        imageHandler.setFpsLabel(fpsLabel);
    }

    static public void demo( final String hostname, final int port, final String username, final String password )
    {
        Ui.buildUi(imageLabel, uptimeLabel, timestampLabel, fpsLabel);

//        final LostConnectionHandlerI lostConnectionHandler = new LostConnectionHandlerI()
//        {
//
//            @Override
//            public void onLostConnection( LostConnectionEvt evt )
//            {
//                connectLoopThread( hostname, port, username, password, this );
//            }
//        };
//
//        connectLoopThread( hostname, port, username, password, lostConnectionHandler );

        final Flowable<State> stateFlowable = NettyDemo.go11(hostname, port, username, password);

        stateFlowable
                .subscribe(
                        state -> {
                            if (state.videoDataText != null) {
                                imageHandler.onReceive(state.videoDataText);
                            }
                        },
                        throwable -> {
                            LOG.error("Trouble: ", throwable);
                            imageHandler.onVideoStopped();
                        },
                        () -> imageHandler.onVideoStopped());
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
