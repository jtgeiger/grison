package com.sibilantsolutions.grison.demo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.sibilantsolutions.grison.driver.foscam.entity.VideoDataTextEntity;
import com.sibilantsolutions.grison.evt.ImageHandlerI;

public class DemoImageHandler implements ImageHandlerI
{

    private JLabel imageLabel;
    private JLabel uptimeLabel;
    private JLabel timestampLabel;
    private JLabel fpsLabel;

    private final AtomicInteger frameCounter = new AtomicInteger();

    // Use current time - 1 just in case the fps calculator fires in the same millisecond, to avoid
    // div by zero.
    private long lastFpsMs = System.currentTimeMillis() - 1;

    public DemoImageHandler() {
        ScheduledExecutorService scheduledExecutorService = Executors
                .newSingleThreadScheduledExecutor(new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r, "fpsCalculator");
                        thread.setDaemon(true);
                        return thread;
                    }
                });
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                long msDiff = now - lastFpsMs;
                int frameCount = frameCounter.getAndSet(0);
                lastFpsMs = now;
                double seconds = msDiff / 1000.0;
                double fps = frameCount / seconds;
                NumberFormat format = DecimalFormat.getInstance();
                format.setMinimumFractionDigits(2);
                format.setMaximumFractionDigits(2);
                final String fpsStr = format.format(fps);
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        fpsLabel.setText(fpsStr);
                    }
                };
                swingThreadInvoke(r);
            }
        };
        scheduledExecutorService.scheduleWithFixedDelay(updater, 0, 1500, TimeUnit.MILLISECONDS);
    }

    private BufferedImage createLostConnectionImage()
    {
        int width = 640;
        int height = 480;
        final Icon icon = imageLabel.getIcon();
        if (icon != null) {
            width = icon.getIconWidth();
            height = icon.getIconHeight();
        }
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(Color.BLUE);
        g.fillRect( 0, 0, bi.getWidth(), bi.getHeight() );
        g.setColor(Color.WHITE);
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
    public void onReceive(VideoDataTextEntity videoData)
    {
        frameCounter.incrementAndGet();
        final ImageIcon icon = new ImageIcon(videoData.videoData());

        final long uptimeMs = videoData.uptimeMs();
        String uptimeDuration = String.format("%dd %02d:%02d:%02d", TimeUnit.MILLISECONDS.toDays(
                uptimeMs), TimeUnit.MILLISECONDS.toHours(uptimeMs) - TimeUnit.DAYS.toHours(
                TimeUnit.MILLISECONDS.toDays(uptimeMs)), TimeUnit.MILLISECONDS.toMinutes(
                uptimeMs) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(
                uptimeMs)), TimeUnit.MILLISECONDS.toSeconds(uptimeMs)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                .toMinutes(uptimeMs)));

        final String uptimeStr = String.valueOf(uptimeMs) + " (" + uptimeDuration + ")";
//        final String timestampStr = String.valueOf(videoData.getTimestampMs()) + " (" + new Date(
//                videoData.getTimestampMs()) + ')';
        final String timestampStr = videoData.timestamp().toString();

        Runnable r = new Runnable() {

            @Override
            public void run()
            {
                imageLabel.setIcon(icon);
                uptimeLabel.setText(uptimeStr);
                timestampLabel.setText(timestampStr);
            }
        };

        swingThreadInvoke(r);
    }

    private void swingThreadInvoke(Runnable r) {
        SwingUtilities.invokeLater(r);
    }

    @Override
    public void onVideoStopped()
    {
        showNoSignalImage();
    }

    public void showNoSignalImage() {
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

    public JLabel getFpsLabel() {
        return fpsLabel;
    }

    public void setFpsLabel(JLabel fpsLabel) {
        this.fpsLabel = fpsLabel;
    }

}
