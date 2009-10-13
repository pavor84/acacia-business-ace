/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.util.converter.cyrillic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 *
 * @author Miro
 */
public class CyrillicConverter {

    public static final int BUFFER_SIZE = 2048;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        File inFile = new File("E:/Works.NB/Acacia/data/AS-products/ASF.txt");
        File outFile = new File("E:/Works.NB/Acacia/data/AS-products/ASF-2.txt");
        Charset charset = Charset.forName("UTF-8");
        InputStream inStream = new FileInputStream(inFile);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), charset));
        MikToUnicodeReader reader = new MikToUnicodeReader(inStream);
        char[] result = new char[BUFFER_SIZE];
        int read;
        while((read = reader.read(result)) > 0) {
            writer.write(result, 0, read);
        }
    }

}
