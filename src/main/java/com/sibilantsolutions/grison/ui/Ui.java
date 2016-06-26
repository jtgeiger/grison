package com.sibilantsolutions.grison.ui;

import com.sibilantsolutions.utils.util.DurationLoggingRunnable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.FlowLayout;
import java.lang.reflect.InvocationTargetException;

public class Ui
{

    private Ui() {}    //Prevent instantiation.

    static public Ui buildUi(final JLabel imageLabel, final JLabel uptimeLabel,
                             final JLabel timestampLabel, final JLabel fpsLabel)
    {
        final Ui ui = new Ui();

        Runnable r = new Runnable() {

            @Override
            public void run()
            {
                ui.buildUiImpl(imageLabel, uptimeLabel, timestampLabel, fpsLabel);
            }

        };

        r = new DurationLoggingRunnable( r, "build ui" );

        try
        {
            SwingUtilities.invokeAndWait( r );
        }
        catch ( InvocationTargetException | InterruptedException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        return ui;
    }


    private void buildUiImpl(JLabel imageLabel, JLabel uptimeLabel, JLabel timestampLabel,
                             JLabel fpsLabel)
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

        //f.pack();
        f.setVisible( true );
    }

}
