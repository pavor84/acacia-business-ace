package com.cosmos.util.alustyle.converter;

import com.cosmos.data.magic.FieldNode;
import com.cosmos.data.magic.FileNode;
import com.cosmos.data.magic.MagicNode;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
        inFileName = inFileName.substring(0, index);
        FileNode fileNode = magicNode.getFileNode(inFileName + ".");
        LineNumberReader reader = new LineNumberReader(new BufferedReader(new InputStreamReader(inStream, Charset.forName("Bg-Mik"))));
        String line;
        List<String> fieldNodeNames = fileNode.getFieldNodeNames();

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(inFileName);
        int rowNumber = 0;
        HSSFRow row = sheet.createRow(rowNumber++);
        int cellNumber = 0;
        for(String fieldNodeName : fieldNodeNames) {
            HSSFCell cell = row.createCell(cellNumber++);
            cell.setCellValue(fieldNodeName);
        }

        while((line = reader.readLine()) != null) {
            System.out.println(line);
            int position = 0;
            cellNumber = 0;
            row = sheet.createRow(rowNumber++);
            for(String fieldNodeName : fieldNodeNames) {
                FieldNode fieldNode = fileNode.getFieldNode(fieldNodeName);
                String picture = fieldNode.getFullPicture();
                int size = picture.length();
                String value = line.substring(position, position + size);
                HSSFCell cell = row.createCell(cellNumber++);
                if(fieldNode.isNumber()) {
                    if(value != null && (value = value.trim()).length() > 0) {
                        cell.setCellValue(Double.parseDouble(value));
                    }
                } else {
                    cell.setCellValue(value);
                }
                position += size + 1;
            }
        }

        File file = new File("AluStyle.xls");
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();
        System.out.println(file.getAbsolutePath());
    }
}
