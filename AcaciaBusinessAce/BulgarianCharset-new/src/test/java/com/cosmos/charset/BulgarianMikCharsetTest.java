/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miro
 */
public class BulgarianMikCharsetTest {

    public BulgarianMikCharsetTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDecodeEncode() {
        System.out.println("testDecodeEncode");
        BulgarianMikCharset bgMikCharset = new BulgarianMikCharset();
        int size = 0x100;
        byte[] sourceBytes = new byte[size];
        for(int i = 0; i < size; i++) {
            sourceBytes[i] = (byte) i;
        }
        String sourceString = new String(sourceBytes);
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        byteBuffer.put(sourceBytes);
        byteBuffer.flip();
        CharBuffer charBuffer = bgMikCharset.decode(byteBuffer);
        byteBuffer = bgMikCharset.encode(charBuffer);
        String targetString = new String(byteBuffer.array());
        assertEquals(targetString, sourceString);
    }
}