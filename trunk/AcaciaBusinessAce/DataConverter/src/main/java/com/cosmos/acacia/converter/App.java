package com.cosmos.acacia.converter;

import com.cosmos.acacia.converter.data.Product;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;

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
        String inputFileName = "E:/Works.NB/Acacia/data/AS-products/parts.txt";
        DataProcessor dataProcessor = new DataProcessor(inputFileName, Product.class);
        List<Product> products = dataProcessor.processData();
        for(Product product : products) {
            System.out.println(product);
        }
    }
}
