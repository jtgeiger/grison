package com.sibilantsolutions.grison.demo;

import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;
import com.sibilantsolutions.grison.evt.ImageHandlerI;
import com.sibilantsolutions.grison.util.Convert;

public class DemoImageHandler implements ImageHandlerI
{

    private JLabel label;

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
            throw new UnsupportedOperationException( "MY TODO!", e );
        }
    }

    public JLabel getLabel()
    {
        return label;
    }

    public void setLabel( JLabel label )
    {
        this.label = label;
    }

}
