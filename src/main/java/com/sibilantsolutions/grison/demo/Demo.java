package com.sibilantsolutions.grison.demo;

import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.client.AudioVideoClient;
import com.sibilantsolutions.grison.evt.AudioHandlerI;
import com.sibilantsolutions.grison.rx.State;
import com.sibilantsolutions.grison.ui.Ui;
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

        final Flowable<State> stateFlowable = AudioVideoClient.stream(hostname, port, username, password);

        stateFlowable
                .subscribe(
                        state -> {
                            if (state.videoDataText != null) {
                                imageHandler.onReceive(state.videoDataText);
                            }

                            if (state.audioDataText != null) {
                                audioHandler.onReceive(state.audioDataText);
                            }
                        },
                        throwable -> {
                            LOG.error("Trouble: ", throwable);
                            imageHandler.onVideoStopped();
                        },
                        () -> imageHandler.onVideoStopped());
    }

}
