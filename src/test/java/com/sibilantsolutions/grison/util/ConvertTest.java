package com.sibilantsolutions.grison.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConvertTest
{

    @Test
    public void testToBigEndian()
    {
        assertEquals( "" + (char)0x00, Convert.toBigEndian( 0 ) );
        assertEquals( "" + (char)0x01, Convert.toBigEndian( 1 ) );
        assertEquals( "" + (char)0xFF, Convert.toBigEndian( 255 ) );
        assertEquals( "" + (char)0x01 + (char)0x00, Convert.toBigEndian( 256 ) );
        assertEquals( "" + (char)0x01 + (char)0xFF, Convert.toBigEndian( 511 ) );

    }

    @Test
    public void testToBigEndianLongInt()
    {
        assertEquals( "" + (char)0x01, Convert.toBigEndian( 1, 1 ) );
        assertEquals( "" + (char)0xFF, Convert.toBigEndian( 255, 1 ) );

        assertEquals( "" + (char)0x00 + (char)0x00, Convert.toBigEndian( 0, 2 ) );
        assertEquals( "" + (char)0x00 + (char)0x01, Convert.toBigEndian( 1, 2 ) );
        assertEquals( "" + (char)0x00 + (char)0xFF, Convert.toBigEndian( 255, 2 ) );
        assertEquals( "" + (char)0x01 + (char)0x00, Convert.toBigEndian( 256, 2 ) );
        assertEquals( "" + (char)0x01 + (char)0xFF, Convert.toBigEndian( 511, 2 ) );

        assertEquals( "" + (char)0x00 + (char)0x00 + (char)0x00 + (char)0x00, Convert.toBigEndian( 0, 4 ) );
        assertEquals( "" + (char)0x00 + (char)0x00 + (char)0x00 + (char)0x01, Convert.toBigEndian( 1, 4 ) );
        assertEquals( "" + (char)0x00 + (char)0x00 + (char)0x00 + (char)0xFF, Convert.toBigEndian( 255, 4 ) );
        assertEquals( "" + (char)0x00 + (char)0x00 + (char)0x01 + (char)0x00, Convert.toBigEndian( 256, 4 ) );
        assertEquals( "" + (char)0x00 + (char)0x00 + (char)0x01 + (char)0xFF, Convert.toBigEndian( 511, 4 ) );
    }

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
    public void testToNum()
    {
        assertEquals( 0x003D918, Convert.toNum( "" + (char)0x00 + (char)0x03 + (char)0xD9 + (char)0x18 ) );
    }

    @Test
    public void testToNumLittleEndian()
    {
        assertEquals( 0x003D918, Convert.toNumLittleEndian( "" + (char)0x18 + (char)0xD9 + (char)0x03 + (char)0x00 ) );
    }

    @Test
    public void testPadRear()
    {
        assertEquals( "abcZZZ", Convert.padRear( "abc", 6, 'Z' ) );
    }

}
