package com.sibilantsolutions.grison.demo;

import java.awt.FlowLayout;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

class DemoUi {

    private DemoUi() {
    }    //Prevent instantiation.

    static DemoUi buildUi(final JLabel imageLabel, final JLabel uptimeLabel,
                          final JLabel timestampLabel, final JLabel fpsLabel, JButton videoStartButton,
                          JButton videoEndButton, JButton audioStartButton, JButton audioEndButton, JButton setTimeButton)
    {
        final DemoUi demoUi = new DemoUi();

        try
        {
            SwingUtilities.invokeAndWait(
                    () -> demoUi.buildUiImpl(imageLabel, uptimeLabel, timestampLabel, fpsLabel,
                            videoStartButton, videoEndButton, audioStartButton, audioEndButton, setTimeButton));
        }
        catch ( InvocationTargetException | InterruptedException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        return demoUi;
    }


    private void buildUiImpl(JLabel imageLabel, JLabel uptimeLabel, JLabel timestampLabel,
                             JLabel fpsLabel, JButton videoStartButton, JButton videoEndButton,
                             JButton audioStartButton, JButton audioEndButton, JButton setTimeButton)
    {
        JFrame f = new JFrame( "Grison" );
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize( 800, 600 );

        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(imageLabel);
        f.getContentPane().add(new JLabel("Uptime:"));
        f.getContentPane().add(uptimeLabel);
        f.getContentPane().add(new JLabel("Timestamp:"));
        f.getContentPane().add(timestampLabel);
        f.getContentPane().add(new JLabel("FPS:"));
        f.getContentPane().add(fpsLabel);
        f.getContentPane().add(videoStartButton);
        f.getContentPane().add(videoEndButton);
        f.getContentPane().add(audioStartButton);
        f.getContentPane().add(audioEndButton);
        f.getContentPane().add(setTimeButton);

        //f.pack();
        f.setVisible(true);
    }

}
