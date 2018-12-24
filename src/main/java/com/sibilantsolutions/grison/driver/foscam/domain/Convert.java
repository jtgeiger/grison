package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public abstract class Convert {
    final static public Charset cs = Charset.forName("ISO-8859-1");

    private Convert() {
    }    //Prevent instantiation.

    static public int byteToNum(byte b) {
        return b & 0xFF;
    }

    static public int getByte(ByteBuffer bb) {
        return byteToNum(bb.get());
    }

    static public long toNum(ByteBuffer bb, int numBytes) {
        byte[] bytes = new byte[numBytes];
        bb.get(bytes);

        return toNum(bytes, 0, numBytes, bb.order());
    }

    static public long toNum(byte[] bytes, int offset, int length) {
        return toNum(bytes, offset, length, ByteOrder.BIG_ENDIAN);
    }

    static public long toNum(byte[] bytes, int offset, int length, ByteOrder byteOrder) {
        long num = 0;

        final int endIndex = offset + length;

        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            while (offset < endIndex) {
                char b = (char) (bytes[offset++] & 0xFF);

                num <<= 8;

                num += b;
            }
        } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            for (int i = endIndex - 1; i >= offset; i--) {
                char b = (char) (bytes[i] & 0xFF);

                num <<= 8;

                num += b;
            }
        } else {
            throw new IllegalArgumentException("Unexpected byte order=" + byteOrder);
        }

        return num;
    }


    public static String get(int length, ByteBuffer b) {
        byte[] bytes = new byte[length];

        b.get(bytes);

        return new String(bytes, 0, bytes.length, Convert.cs);
    }

    public static String padFront(String val, int numBytes, char padChar) {
        StringBuilder buf = new StringBuilder(val);

        for (; buf.length() < numBytes; ) {
            buf.insert(0, padChar);
        }

        return buf.toString();
    }

    public static String padRear(String val, int numBytes, char padChar) {
        StringBuilder buf = new StringBuilder(val);

        for (; buf.length() < numBytes; ) {
            buf.append(padChar);
        }

        return buf.toString();
    }

    public static String padRearOrTruncate(String val, int numBytes, char padChar) {
        if (val.length() > numBytes)
            return val.substring(0, numBytes);

        return padRear(val, numBytes, padChar);
    }

    static public void put(String s, ByteBuffer b) {
        b.put(s.getBytes(Convert.cs));
    }

    public static String toBigEndian(long num) {
        StringBuilder buf = new StringBuilder();

        do {
            char cur = (char) (num & 0xFF);
            buf.insert(0, cur);
            num >>= 8;

        } while (num > 0);

        return buf.toString();
    }

    static public String toBigEndian(long num, int numBytes) {
        String val = toBigEndian(num);

        if (val.length() > numBytes)
            throw new IllegalArgumentException("Overflow: num=" + num + ", numBytes=" + numBytes);

        return padFront(val, numBytes, (char) 0);
    }

    public static String toLittleEndian(long num) {
        StringBuilder buf = new StringBuilder();

        do {
            char cur = (char) (num & 0xFF);
            buf.append(cur);
            num >>= 8;

        } while (num > 0);

        return buf.toString();
    }

    static public String toLittleEndian(long num, int numBytes) {
        String val = toLittleEndian(num);

        if (val.length() > numBytes)
            throw new IllegalArgumentException("Overflow: num=" + num + ", numBytes=" + numBytes);

        return padRear(val, numBytes, (char) 0);
    }

    static public long toNum(String str) {
        long num = 0;

        for (int i = 0; i < str.length(); i++) {
            num <<= 8;

            char c1 = str.charAt(i);
            num += c1;
        }

        return num;
    }

    static public long toNumLittleEndian(String str) {
        long num = 0;

        for (int i = str.length() - 1; i >= 0; i--) {
            num <<= 8;

            char c1 = str.charAt(i);
            num += c1;
        }

        return num;
    }

}
