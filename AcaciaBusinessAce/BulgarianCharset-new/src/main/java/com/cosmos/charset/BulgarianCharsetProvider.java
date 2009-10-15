/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.charset;

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Miro
 */
public class BulgarianCharsetProvider extends CharsetProvider {

    private static final List<Charset> charsets = Arrays.asList((Charset) new BulgarianMikCharset());

    @Override
    public Iterator<Charset> charsets() {
        return charsets.iterator();
    }

    @Override
    public Charset charsetForName(String charsetName) {
        for(Charset charset : charsets) {
            if(charsetName.equalsIgnoreCase(charset.name())) {
                return charset;
            }
            for(String name : charset.aliases()) {
                if(charsetName.equalsIgnoreCase(name)) {
                    return charset;
                }
            }
        }

        return null;
    }
}
