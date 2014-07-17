package com.sibilantsolutions.grison.ui;

import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;
import com.sibilantsolutions.grison.evt.ImageHandlerI;
import com.sibilantsolutions.grison.util.Convert;
import com.sibilantsolutions.iptools.util.DurationLoggingRunnable;

public class Ui
{

    private ImageHandlerI imageHandler;

    private Ui() {}    //Prevent instantiation.

    static public Ui buildUi()
    {
        final Ui ui = new Ui();

        Runnable r = new Runnable() {

            @Override
            public void run()
            {
                ui.buildUiImpl();
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


    private void buildUiImpl()
    {
        JFrame f = new JFrame( "Grison" );
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        f.setSize( 800, 600 );

        final JLabel label = new JLabel();

        f.getContentPane().add( label );

        //f.pack();
        f.setVisible( true );

        imageHandler = new ImageHandlerI() {

            @Override
            public void onReceive( VideoDataText videoData )
            {
                byte[] imageData = videoData.getDataContent().getBytes( Convert.cs );

                final ImageIcon ii = new ImageIcon( imageData );

                Runnable r = new Runnable() {

                    @Override
                    public void run()
                    {
                        label.setIcon( ii );
                    }
                };

                try
                {
                    SwingUtilities.invokeAndWait( r );
                }
                catch ( InvocationTargetException | InterruptedException e )
                {
                    // TODO Auto-generated catch block
                    throw new UnsupportedOperationException( "OGTE TODO!", e );
                }
            }
        };

    }

    public ImageHandlerI getImageHandler()
    {
        return imageHandler;
    }

}
