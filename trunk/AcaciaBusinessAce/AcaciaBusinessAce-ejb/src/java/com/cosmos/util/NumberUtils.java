/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.util;

import java.math.BigInteger;
import java.util.UUID;

/**
 *
 * @author Miro
 */
public class NumberUtils {

    public static final UUID ZERO_UUID = new UUID(0, 0);

    public static byte[] toByteArray(short value) {
        return toByteArray(value, 2);
    }

    public static byte[] toByteArray(int value) {
        return toByteArray(value, 4);
    }

    public static byte[] toByteArray(long value) {
        return toByteArray(value, 8);
    }

    public static byte[] toByteArray(UUID uuid) {
        byte[] firstBytes = toByteArray(uuid.getMostSignificantBits());
        byte[] secondBytes = toByteArray(uuid.getLeastSignificantBits());
        return append(firstBytes, secondBytes);
    }

    private static byte[] toByteArray(long value, int length) {
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = (byte) (value & 0xFF);
            value >>>= 8;
        }

        return result;
    }

    /**
     * Appends two bytes array into one.
     *
     * @param a A byte[].
     * @param b A byte[].
     * @return A byte[].
     */
    public static byte[] append(byte[] a, byte[] b) {
        byte[] z = new byte[a.length + b.length];
        System.arraycopy(a, 0, z, 0, a.length);
        System.arraycopy(b, 0, z, a.length, b.length);
        return z;
    }

    public static short toShort(byte[] value) {
        return toShort(value, 0);
    }

    public static short toShort(byte[] value, int beginPos) {
        return (short) toNumber(value, beginPos, 2);
    }

    public static int toInt(byte[] value) {
        return toInt(value, 0);
    }

    public static int toInt(byte[] value, int beginPos) {
        return (int) toNumber(value, beginPos, 4);
    }

    public static long toLong(byte[] value) {
        return toLong(value, 0);
    }

    public static long toLong(byte[] value, int beginPos) {
        return toNumber(value, beginPos, 8);
    }

    private static long toNumber(byte[] value, int beginPos, int length) {
        if (value == null || value.length == 0) {
            return 0;
        }

        if (length > 8) {
            length = 8;
        }

        if ((beginPos + length) > value.length) {
            length = value.length - beginPos;
        }

        if (length == 0) {
            return 0;
        }

        int endPos = beginPos + length;
        long result = 0;
        for (int i = (endPos - 1); i >= beginPos; i--) {
            int b = value[i] & 0xFF;
            result |= b;
            if (i > beginPos) {
                result <<= 8;
            }
        }

        return result;
    }

    public static UUID toUUID(byte[] value) {
        return toUUID(value, 0);
    }

    public static UUID toUUID(byte[] value, int beginPos) {
        if (value.length < beginPos + 16) {
            throw new IllegalArgumentException("The length (" + value.length +
                    ") of bytes is less than required (" + beginPos + 16 + ").");
        }

        long mostSigBits = toLong(value, beginPos);
        long leastSigBits = toLong(value, beginPos + 8);
        return new UUID(mostSigBits, leastSigBits);
    }

    public static UUID toUUID(BigInteger intValue) {
        return toUUID(intValue.toByteArray());
    }

    public static BigInteger toBigInteger(UUID uuid) {
        return new BigInteger(toByteArray(uuid));
    }

    public static void main(String[] args) {
        try {
            long l = 123456;
            System.out.println("l: " + l);
            byte[] ba = toByteArray(l);
            l = toLong(ba);
            System.out.println("l: " + l);

            l = -123456;
            System.out.println("l: " + l);
            ba = toByteArray(l);
            l = toLong(ba);
            System.out.println("l: " + l);

            int i = 123456;
            System.out.println("i: " + i);
            ba = toByteArray(i);
            i = toInt(ba);
            System.out.println("i: " + i);

            i = -123456;
            System.out.println("i: " + i);
            ba = toByteArray(i);
            i = toInt(ba);
            System.out.println("i: " + i);

            short s = 12345;
            System.out.println("s: " + s);
            ba = toByteArray(s);
            s = toShort(ba);
            System.out.println("s: " + s);

            s = -12345;
            System.out.println("s: " + s);
            ba = toByteArray(s);
            s = toShort(ba);
            System.out.println("s: " + s);

            UUID uuid1 = UUID.randomUUID();
            System.out.println("uuid1: " + uuid1);
            ba = toByteArray(uuid1);
            UUID uuid2 = toUUID(ba);
            System.out.println("uuid2: " + uuid2);
            System.out.println("uuid1.equals(uuid2): " + uuid1.equals(uuid2));

            BigInteger intValue1 = toBigInteger(uuid1);
            uuid2 = toUUID(intValue1);
            System.out.println("uuid1.equals(uuid2): " + uuid1.equals(uuid2));
            BigInteger intValue2 = toBigInteger(uuid2);
            System.out.println("intValue1.equals(intValue2): " + intValue1.equals(intValue2));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
