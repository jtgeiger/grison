package com.sibilantsolutions.grison.demo;

import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;
import com.sibilantsolutions.grison.evt.ImageHandlerI;
import com.sibilantsolutions.grison.evt.VideoStoppedEvt;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class DemoImageHandler implements ImageHandlerI
{

    private JLabel imageLabel;
    private JLabel uptimeLabel;
    private JLabel timestampLabel;

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

    public JLabel getImageLabel()
    {
        return imageLabel;
    }

    public void setImageLabel(JLabel imageLabel)
    {
        this.imageLabel = imageLabel;
    }

    @Override
    public void onReceive(final VideoDataText videoData)
    {
        Runnable r = new Runnable() {

            @Override
            public void run()
            {
                byte[] imageData = videoData.getDataContent();

                imageLabel.setIcon(new ImageIcon(imageData));
                uptimeLabel.setText(String.valueOf(videoData.getUptimeMs()));
                timestampLabel.setText(String.valueOf(videoData.getTimestampMs()) + " (" + new Date(videoData.getTimestampMs()) + ')');
            }
        };

        swingThreadInvoke(r);
    }

    private void swingThreadInvoke(Runnable r) {
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
        final BufferedImage lostConnectionImage = createLostConnectionImage();

        Runnable r = new Runnable() {

            @Override
            public void run() {
                imageLabel.setIcon(new ImageIcon(lostConnectionImage));
                uptimeLabel.setText("");
                timestampLabel.setText("");
            }
        };

        swingThreadInvoke(r);
    }

    public JLabel getUptimeLabel() {
        return uptimeLabel;
    }

    public void setUptimeLabel(JLabel uptimeLabel) {
        this.uptimeLabel = uptimeLabel;
    }

    public JLabel getTimestampLabel() {
        return timestampLabel;
    }

    public void setTimestampLabel(JLabel timestampLabel) {
        this.timestampLabel = timestampLabel;
    }

}
