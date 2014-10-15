package com.sibilantsolutions.grison.sound.adpcm;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.sibilantsolutions.utils.util.ResourceReader;

public class AdpcmDecoderTest
{
    //The ADPCM samples were decoded into PCM all in order so they have to be tested in order;
    //the decoder maintains state between decodes.

    @Test
    public void testDecodeByteArray()
    {
        AdpcmDecoder decoder = new AdpcmDecoder();

        byte[] raw;
        byte[] actual;
        byte[] expected;

        raw = ResourceReader.readResourceAsBytes( "/samples/sound/audio000.adpcm" );
        actual = decoder.decode( raw );
        expected = ResourceReader.readResourceAsBytes( "/samples/sound/audio000.pcm" );
        assertArrayEquals( expected, actual );

        raw = ResourceReader.readResourceAsBytes( "/samples/sound/audio001.adpcm" );
        actual = decoder.decode( raw );
        expected = ResourceReader.readResourceAsBytes( "/samples/sound/audio001.pcm" );
        assertArrayEquals( expected, actual );
    }

    @Test
    public void testDecodeByteArrayAll()
    {
        final int count = 218;

        AdpcmDecoder decoder = new AdpcmDecoder();

        for ( int i = 0; i < count; i++ )
        {
            String base = "/samples/sound/audio";
            if ( i < 100 )
            {
                if ( i < 10 )
                    base += "00";
                else
                    base += "0";
            }

            base += i;

            byte[] raw = ResourceReader.readResourceAsBytes( base + ".adpcm" );
            byte[] actual = decoder.decode( raw );
            byte[] expected = ResourceReader.readResourceAsBytes( base + ".pcm" );

            assertArrayEquals( expected, actual );
        }
    }

}
