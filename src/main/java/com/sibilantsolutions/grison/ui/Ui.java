package com.sibilantsolutions.grison.ui;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.sibilantsolutions.utils.util.DurationLoggingRunnable;

public class Ui
{

    private Ui() {}    //Prevent instantiation.

    static public Ui buildUi( final JLabel label )
    {
        final Ui ui = new Ui();

        Runnable r = new Runnable() {

            @Override
            public void run()
            {
                ui.buildUiImpl( label );
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


    private void buildUiImpl( JLabel label )
    {
        JFrame f = new JFrame( "Grison" );
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        f.setSize( 800, 600 );

        f.getContentPane().add( label );

        //f.pack();
        f.setVisible( true );
    }

}
