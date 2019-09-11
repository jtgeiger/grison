package com.sibilantsolutions.grison.sound.adpcm;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.sibilantsolutions.grison.net.netty.codec.parse.ResourceToByteBuf;

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

        raw = new ResourceToByteBuf().apply("/samples/sound/audio000.adpcm").array();
        actual = decoder.decode( raw );
        expected = new ResourceToByteBuf().apply("/samples/sound/audio000.pcm").array();
        assertArrayEquals( expected, actual );

        raw = new ResourceToByteBuf().apply("/samples/sound/audio001.adpcm").array();
        actual = decoder.decode( raw );
        expected = new ResourceToByteBuf().apply("/samples/sound/audio001.pcm").array();
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

            byte[] raw = new ResourceToByteBuf().apply(base + ".adpcm").array();
            byte[] actual = decoder.decode( raw );
            byte[] expected = new ResourceToByteBuf().apply(base + ".pcm").array();

            assertArrayEquals( expected, actual );
        }
    }

}
