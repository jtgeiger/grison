package com.sibilantsolutions.grison.demo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;
import com.sibilantsolutions.grison.evt.ImageHandlerI;
import com.sibilantsolutions.grison.evt.VideoStoppedEvt;

public class DemoImageHandler implements ImageHandlerI
{

    private JLabel label;

    private BufferedImage createLostConnectionImage()
    {
        BufferedImage bi = new BufferedImage( 640, 480, BufferedImage.TYPE_INT_RGB );
        Graphics2D g = bi.createGraphics();
        g.setColor( Color.YELLOW );
        g.fillRect( 0, 0, bi.getWidth(), bi.getHeight() );
        g.setColor( Color.BLACK );
        g.drawString( "LOST CONNECTION", 25, 25 );
        return bi;
    }

    public JLabel getLabel()
    {
        return label;
    }

    public void setLabel( JLabel label )
    {
        this.label = label;
    }

    @Override
    public void onReceive( VideoDataText videoData )
    {
        byte[] imageData = videoData.getDataContent();

        setImage( new ImageIcon( imageData ) );
    }

    private void setImage( final ImageIcon imageIcon )
    {
        Runnable r = new Runnable() {

            @Override
            public void run()
            {
                label.setIcon( imageIcon );
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

    @Override
    public void onVideoStopped( VideoStoppedEvt videoStoppedEvt )
    {
        BufferedImage lostConnectionImage = createLostConnectionImage();

        setImage( new ImageIcon( lostConnectionImage ) );
    }

}
