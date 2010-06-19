package com.cosmos.util.alustyle.converter;

import com.cosmos.data.magic.FileNode;
import com.cosmos.data.magic.MagicNode;
import java.io.File;
import java.io.FileInputStream;

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

        File inFile = new File("company.txt");
        FileInputStream inStream = new FileInputStream(inFile);
        inFileName = inFile.getName();
        int index = inFileName.lastIndexOf('.');
        inFileName = inFileName.substring(0, index + 1);
        FileNode fileNode = magicNode.getFileNode(inFileName);
    }
}
