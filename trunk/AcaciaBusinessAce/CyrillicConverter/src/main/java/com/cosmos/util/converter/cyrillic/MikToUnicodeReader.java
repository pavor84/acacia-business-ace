/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.util.converter.cyrillic;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.CharBuffer;

/**
 *
 * @author Miro
 */
public class MikToUnicodeReader extends Reader {

    public static final int CYRILLIC_CAPITAL_LETTER_A = 0x0410;
    public static final int CYRILLIC_CAPITAL_LETTER_YA = 0x042F;
    public static final int CYRILLIC_SMALL_LETTER_A = 0x0430;
    public static final int CYRILLIC_SMALL_LETTER_YA = 0x044F;
    //
    public static final int BG_DOS_MIK_CAPITAL_LETTER_A = 0x80;
    public static final int BG_DOS_MIK_CAPITAL_LETTER_YA = 0x9F;
    public static final int BG_DOS_MIK_SMALL_LETTER_A = 0xA0;
    public static final int BG_DOS_MIK_SMALL_LETTER_YA = 0xBF;
    //
    protected static final int BG_DOS_MIK_UTF_DIFFERENCE = CYRILLIC_CAPITAL_LETTER_A - BG_DOS_MIK_CAPITAL_LETTER_A;
    //
    protected static final int BUFFER_SIZE = 2048;
    //
    private InputStream sourceInputStream;
    private CharBuffer charBuffer;
    private boolean endOfStream;

    public MikToUnicodeReader(InputStream sourceInputStream) {
        this.sourceInputStream = sourceInputStream;
        charBuffer = CharBuffer.allocate(BUFFER_SIZE);
        charBuffer.flip();
    }

    @Override
    public void close() throws IOException {
        sourceInputStream.close();
    }

    @Override
    public int read(char[] targetBuffer, int offset, int length) throws IOException {
        if(endOfStream) {
            return -1;
        }

        synchronized (lock) {
            return readInputStream(targetBuffer, offset, length);
        }
    }

    private int readInputStream(char[] targetBuffer, int offset, int length) throws IOException {
        if(endOfStream) {
            return -1;
        }

        if(length == 0) {
            return 0;
        }

            int read = 0;
            int remaining;
            if((remaining = charBuffer.remaining()) >= length) {
                charBuffer.get(targetBuffer, offset, length);
                return length;
            } else if(remaining > 0) {
                charBuffer.get(targetBuffer, offset, remaining);
                read += remaining;
                offset += remaining;
                length -= remaining;
            }
            charBuffer.clear();

            byte[] buffer = new byte[BUFFER_SIZE];
            while(length > 0 && (remaining = sourceInputStream.read(buffer)) > 0) {
                for(int i = 0; i < remaining; i++) {
                    charBuffer.put(mikToUnicode(buffer[i]));
                }
                charBuffer.flip();
                if((remaining = readInputStream(targetBuffer, offset, length)) < 0) {
                    if(read > 0) {
                        return read;
                    } else {
                        return remaining;
                    }
                } else {
                    read += remaining;
                    offset += remaining;
                    length -= remaining;
                }
            }

            if(remaining < 0) {
                endOfStream = true;
                if(read > 0) {
                    return read;
                } else {
                    return remaining;
                }
            }

            return read;
    }

    private char mikToUnicode(byte ch) {
        int charValue = ch & 0xFF;
        if(charValue >= BG_DOS_MIK_CAPITAL_LETTER_A && charValue <= BG_DOS_MIK_SMALL_LETTER_YA) {
            charValue += BG_DOS_MIK_UTF_DIFFERENCE;
        }

        return (char) charValue;
    }
}
