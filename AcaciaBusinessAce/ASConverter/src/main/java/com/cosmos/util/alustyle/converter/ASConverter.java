package com.cosmos.util.alustyle.converter;

import com.cosmos.data.magic.MagicNode;

/**
 * Hello world!
 *
 */
public class ASConverter {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
        String inFileName = "ASF-int.txt";
        DataLineProcessor dataProcessor = new DataLineProcessor(inFileName);
        MagicNode magicNode = dataProcessor.processData();
    }
}
