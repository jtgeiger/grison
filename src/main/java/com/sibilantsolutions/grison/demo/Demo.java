package com.sibilantsolutions.grison.demo;

import java.net.InetSocketAddress;

import javax.swing.JLabel;

import com.sibilantsolutions.grison.driver.foscam.net.FoscamSession;
import com.sibilantsolutions.grison.evt.AudioHandlerI;
import com.sibilantsolutions.grison.ui.Ui;

public class Demo
{
//    final static private Logger log = LoggerFactory.getLogger( Demo.class );

    static private AudioHandlerI audioHandler = new DemoAudioHandler();
    static private JLabel label = new JLabel();
    static private DemoImageHandler imageHandler = new DemoImageHandler();
    static
    {
        imageHandler.setLabel( label );
    }

    static public void demo( final String hostname, final int port, String username, String password )
    {
        Ui.buildUi( label );

        FoscamSession session = FoscamSession.connect( new InetSocketAddress( hostname, port ),
                username, password, audioHandler, imageHandler );

        session.audioStart();
//        session.talkStart();
        session.videoStart();

    }

}
