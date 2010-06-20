package com.cosmos.util.alustyle.converter;

import com.cosmos.data.magic.FieldNode;
import com.cosmos.data.magic.FileNode;
import com.cosmos.data.magic.MagicNode;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.util.Arrays;
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

    private File dataFolder;
    private HSSFWorkbook workbook;
    private MagicNode magicNode;

    public ASConverter(String[] args) {
        init(args);
    }

    private void init(String[] args) {
        if(args.length < 2 || "-path".equalsIgnoreCase(args[0])) {
            System.out.println("ASConverter -path data\nTrying with defaults.");
            args = new String[] {"-path", "data"};
        }

        dataFolder = new File(args[1]);
        if(!dataFolder.exists() || !dataFolder.isDirectory()) {
            System.out.println("Data folder \"" + dataFolder.getAbsolutePath() + "\" doesn't exists.");
            System.exit(-1);
        }
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public HSSFWorkbook getWorkbook() {
        if(workbook == null) {
            workbook = new HSSFWorkbook();
        }

        return workbook;
    }

    public HSSFSheet getSheet(String name) {
        HSSFWorkbook wb = getWorkbook();
        HSSFSheet sheet;
        if((sheet = wb.getSheet(name)) == null) {
            sheet = wb.createSheet(name);
        }

        return sheet;
    }

    public void storeWorkbook() throws IOException {
        File file = new File(getDataFolder(), "AluStyle.xls");
        FileOutputStream out = new FileOutputStream(file);
        getWorkbook().write(out);
        out.flush();
        out.close();
        System.out.println(file.getAbsolutePath());
    }

    public MagicNode getMagicNode() throws IOException {
        if(magicNode == null) {
            String inFileName = "ASF.dm";
            File inFile = new File(getDataFolder(), inFileName);
            DataLineProcessor dataProcessor = new DataLineProcessor(inFile);
            magicNode = dataProcessor.processData();
        }

        return magicNode;
    }

    public List<File> getDataFiles() {
        return Arrays.asList(getDataFolder().listFiles(new TextFileFilter()));
    }

    public void convert() throws IOException {
        for(File txtFile : getDataFiles()) {
            convert(txtFile);
        }
        storeWorkbook();
    }

    private LineNumberReader getLineReader(File txtFile) throws IOException {
        FileInputStream inStream = new FileInputStream(txtFile);
        return new LineNumberReader(new BufferedReader(new InputStreamReader(inStream, Charset.forName("Bg-Mik"))));
    }

    public void convert(File txtFile) throws IOException {
        String fileName = txtFile.getName();
        int index = fileName.lastIndexOf('.');
        fileName = fileName.substring(0, index);
        FileNode fileNode = getMagicNode().getFileNode(fileName + ".");
        LineNumberReader reader = getLineReader(txtFile);
        String line;
        List<String> fieldNodeNames = fileNode.getFieldNodeNames();

        HSSFSheet sheet = getSheet(fileName);
        int rowNumber = 0;
        HSSFRow row = sheet.createRow(rowNumber++);
        int cellNumber = 0;
        for(String fieldNodeName : fieldNodeNames) {
            HSSFCell cell = row.createCell(cellNumber++);
            cell.setCellValue(fieldNodeName);
        }

        while((line = reader.readLine()) != null) {
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
    }

    public static void main(String[] args) throws Exception {
        ASConverter converter = new ASConverter(args);
        converter.convert();
    }

    private static class TextFileFilter implements FileFilter {

        @Override
        public boolean accept(File pathname) {
            if(!pathname.isFile()) {
                return false;
            }

            String fileName = pathname.getName();
	    int index = fileName.lastIndexOf('.');
            String ext = fileName.substring(index + 1);
            return "txt".equalsIgnoreCase(ext);
        }
    }
}
