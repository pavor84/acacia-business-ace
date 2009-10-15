package com.cosmos.acacia.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * Hello world!
 *
 */
public class App {
public static final int BUFFER_SIZE = 2048;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        File inFile = new File("E:/Works.NB/Acacia/data/AS-products/ASF.txt");
        Charset charset = Charset.forName("Bg-Mik");
        System.out.println("charset=" + charset);
        InputStream inStream = new FileInputStream(inFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, charset));

        File outFile = new File("E:/Works.NB/Acacia/data/AS-products/ASF-2.txt");
        charset = Charset.forName("UTF-8");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), charset));
        char[] result = new char[BUFFER_SIZE];
        int read;
        while((read = reader.read(result)) > 0) {
            writer.write(result, 0, read);
        }
    }
}
