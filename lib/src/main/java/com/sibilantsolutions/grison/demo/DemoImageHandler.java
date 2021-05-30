package com.sibilantsolutions.grison.demo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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

    private static final String KEY = DemoImageHandler.class.getName();

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
                .newSingleThreadScheduledExecutor(r -> {
                    Thread thread = new Thread(r, "fpsCalculator");
                    thread.setDaemon(true);
                    return thread;
                });
        Runnable updater = () -> {
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
            Runnable r = () -> fpsLabel.setText(fpsStr);
            swingThreadInvoke(r);
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
        //We'll get invoked for every state change (including audio), but only process if the image changed.
        final Object clientProperty = imageLabel.getClientProperty(KEY);
        if (videoData.equals(clientProperty)) {
            return;
        }

        imageLabel.putClientProperty(KEY, videoData);

        frameCounter.incrementAndGet();
        final ImageIcon icon = new ImageIcon(videoData.videoData());
        final Duration uptime = videoData.uptime();

        final String uptimeDuration = String.format("%dd %02d:%02d:%02d.%03d",
                uptime.toDays(),
                uptime.minusHours(TimeUnit.DAYS.toHours(uptime.toDays())).toHours(),
                uptime.minusMinutes(TimeUnit.HOURS.toMinutes(uptime.toHours())).toMinutes(),
                uptime.minusSeconds(TimeUnit.MINUTES.toSeconds(uptime.toMinutes())).getSeconds(),
                uptime.minusMillis(TimeUnit.SECONDS.toMillis(uptime.getSeconds())).toMillis());

        final String timestampStr = videoData.timestamp().atZone(ZoneId.systemDefault()).toString();

        Runnable r = () -> {
            imageLabel.setIcon(icon);
            uptimeLabel.setText(uptimeDuration);
            timestampLabel.setText(timestampStr);
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

        Runnable r = () -> {
            imageLabel.setIcon(new ImageIcon(lostConnectionImage));
            uptimeLabel.setText("");
            timestampLabel.setText("");
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
