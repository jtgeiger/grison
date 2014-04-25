package com.sibilantsolutions.grison.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConvertTest
{

    @Test
    public void testToLittleEndianLong()
    {
        assertEquals( "" + (char)0x00, Convert.toLittleEndian( 0 ) );
        assertEquals( "" + (char)0x01, Convert.toLittleEndian( 1 ) );
        assertEquals( "" + (char)0xFF, Convert.toLittleEndian( 255 ) );
        assertEquals( "" + (char)0x00 + (char)0x01, Convert.toLittleEndian( 256 ) );
        assertEquals( "" + (char)0xFF + (char)0x01, Convert.toLittleEndian( 511 ) );
    }

    @Test
    public void testToLittleEndianLongInt()
    {
        assertEquals( "" + (char)0x01, Convert.toLittleEndian( 1, 1 ) );
        assertEquals( "" + (char)0xFF, Convert.toLittleEndian( 255, 1 ) );

        assertEquals( "" + (char)0x00 + (char)0x00, Convert.toLittleEndian( 0, 2 ) );
        assertEquals( "" + (char)0x01 + (char)0x00, Convert.toLittleEndian( 1, 2 ) );
        assertEquals( "" + (char)0xFF + (char)0x00, Convert.toLittleEndian( 255, 2 ) );
        assertEquals( "" + (char)0x00 + (char)0x01, Convert.toLittleEndian( 256, 2 ) );
        assertEquals( "" + (char)0xFF + (char)0x01, Convert.toLittleEndian( 511, 2 ) );

        assertEquals( "" + (char)0x00 + (char)0x00 + (char)0x00 + (char)0x00, Convert.toLittleEndian( 0, 4 ) );
        assertEquals( "" + (char)0x01 + (char)0x00 + (char)0x00 + (char)0x00, Convert.toLittleEndian( 1, 4 ) );
        assertEquals( "" + (char)0xFF + (char)0x00 + (char)0x00 + (char)0x00, Convert.toLittleEndian( 255, 4 ) );
        assertEquals( "" + (char)0x00 + (char)0x01 + (char)0x00 + (char)0x00, Convert.toLittleEndian( 256, 4 ) );
        assertEquals( "" + (char)0xFF + (char)0x01 + (char)0x00 + (char)0x00, Convert.toLittleEndian( 511, 4 ) );
    }

    @Test
    public void testPadRear()
    {
        assertEquals( "abcZZZ", Convert.padRear( "abc", 6, 'Z' ) );
    }

}
