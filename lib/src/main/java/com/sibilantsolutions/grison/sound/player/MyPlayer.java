package com.sibilantsolutions.grison.sound.player;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

//TODO: Method to stop playback; use of stop(), drain() and close() (we already open() and start()).
public class MyPlayer
{
    //final static private Logger log = LoggerFactory.getLogger( MyPlayer.class );

    final static private int BUF_SIZE = 4096;

    private AudioFormat audioFormat;

    private SourceDataLine dataLine;

    private byte[] buf = new byte[BUF_SIZE];

    public MyPlayer( AudioFormat audioFormat )
    {
        this.audioFormat = audioFormat;

        init();
    }

    public void feed( InputStream ins )
    {
        AudioInputStream audioInputStream =
                new AudioInputStream( ins, audioFormat, AudioSystem.NOT_SPECIFIED );

        try
        {
            for ( int numRead; ( numRead = audioInputStream.read( buf ) ) != -1; )
            {
                dataLine.write( buf, 0, numRead );
            }
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }
    }

    private void init()
    {
        Info info = new Info( SourceDataLine.class, audioFormat );

        try
        {
            dataLine = (SourceDataLine)AudioSystem.getLine( info );
        }
        catch ( LineUnavailableException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        try
        {
            dataLine.open( audioFormat );
        }
        catch ( LineUnavailableException e )
        {
            // TODO Auto-generated catch block
            throw new UnsupportedOperationException( "OGTE TODO!", e );
        }

        dataLine.start();
    }

}
