package com.sibilantsolutions.grison.demo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.client.AudioVideoClient;
import com.sibilantsolutions.grison.evt.AudioHandlerI;
import com.sibilantsolutions.grison.rx.State;
import com.sibilantsolutions.grison.rx.client.OpClientImpl;
import com.sibilantsolutions.grison.rx.net.ChannelSender;
import io.netty.channel.Channel;
import io.reactivex.Flowable;

public class Demo
{
    final static private Logger LOG = LoggerFactory.getLogger(Demo.class);

    static private AudioHandlerI audioHandler = new DemoAudioHandler();
    static private JLabel imageLabel = new JLabel();
    static private JLabel uptimeLabel = new JLabel();
    static private JLabel timestampLabel = new JLabel();
    static private JLabel fpsLabel = new JLabel();
    static private JButton videoEndButton = new JButton("Video End");
    static private JButton videoStartButton = new JButton("Video Start");
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
        DemoUi.buildUi(imageLabel, uptimeLabel, timestampLabel, fpsLabel, videoStartButton, videoEndButton);

        final MyVideoStartActionListener videoStartActionListener = new MyVideoStartActionListener();
        videoStartButton.addActionListener(videoStartActionListener);

        final MyVideoEndActionListener videoEndActionListener = new MyVideoEndActionListener();
        videoEndButton.addActionListener(videoEndActionListener);

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

                            videoStartActionListener.operationChannel = state.operationChannel;
                            videoEndActionListener.operationChannel = state.operationChannel;
                        },
                        throwable -> {
                            LOG.error("Trouble: ", throwable);
                            imageHandler.onVideoStopped();
                        },
                        () -> imageHandler.onVideoStopped());
    }

    private static class MyVideoEndActionListener implements ActionListener {

        private Channel operationChannel;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (operationChannel != null) {
                new OpClientImpl(new ChannelSender(operationChannel)).videoEnd().subscribe();
            }
            imageHandler.onVideoStopped();
        }
    }

    private static class MyVideoStartActionListener implements ActionListener {

        private Channel operationChannel;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (operationChannel != null) {
                new OpClientImpl(new ChannelSender(operationChannel)).videoStart().subscribe();
            }

        }
    }

}
