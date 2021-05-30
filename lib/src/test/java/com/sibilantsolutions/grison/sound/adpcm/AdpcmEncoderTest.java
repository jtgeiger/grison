package com.sibilantsolutions.grison.sound.adpcm;


public class AdpcmEncoderTest
{
/* 7/1/14 TODO: This SHOULD work, but doesn't; the encoding doesn't match the original samples.
    @Test
    public void testEncodeByteArray()
    {
        AdpcmEncoder encoder = new AdpcmEncoder();

        byte[] raw;
        byte[] actual;
        byte[] expected;

        raw = ResourceLoader.loadResourceAsBytes( "/samples/sound/audio000.pcm" );
        actual = encoder.encode( raw );
        expected = ResourceLoader.loadResourceAsBytes( "/samples/sound/audio000.adpcm" );
        assertArrayEquals( expected, actual );

        raw = ResourceLoader.loadResourceAsBytes( "/samples/sound/audio001.pcm" );
        actual = encoder.encode( raw );
        expected = ResourceLoader.loadResourceAsBytes( "/samples/sound/audio001.adpcm" );
        assertArrayEquals( expected, actual );
    }
*/
}
