/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.util.alustyle.converter;

import com.cosmos.data.magic.FieldNode;
import com.cosmos.data.magic.FileNode;
import com.cosmos.data.magic.MagicNode;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/*
VRSN=560
[VRSN, 560]
FILE={NAME="%AluPath%ctrl.usr",DESC="Контрол",QRY=1,MDFY=1,DEL=1,PRG=1,DBNAME="Btrieve",ENCR=N,
[FILE, {NAME, "%AluPath%ctrl.usr",DESC, "Контрол",QRY, 1,MDFY, 1,DEL, 1,PRG, 1,DBNAME, "Btrieve",ENCR, N,]
FLD={DESC="Номер на поле",PIC="#4",STRG=4,ATTR=N,SIZ=2,WHLE=4,TYP=1,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
[FLD, {DESC, "Номер на поле",PIC, "#4",STRG, 4,ATTR, N,SIZ, 2,WHLE, 4,TYP, 1,HLP, 2,RSRCE, {MDFY, Y,TRNS, S,DEF, N,MOD, B,CTRL, E,CTRL_STP, "         0.00000",NUL_ALW, N}},]
FLD={DESC="Стойност на поле",PIC="#12.##",STRG=6,ATTR=N,SIZ=8,WHLE=12,DECI=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
[FLD, {DESC, "Стойност на поле",PIC, "#12.##",STRG, 6,ATTR, N,SIZ, 8,WHLE, 12,DECI, 2,RSRCE, {MDFY, Y,TRNS, S,DEF, N,MOD, B,CTRL, E,CTRL_STP, "         0.00000",NUL_ALW, N}},]
KEY={DESC="по номер на ПОЛЕ",MOD=S,DIR=T,RNG=Q,KEY_TYPE=R,
[KEY, {DESC, "по номер на ПОЛЕ",MOD, S,DIR, T,RNG, Q,KEY_TYPE, R,]
SEG={SIZ=2,FLD=1,DIR=A}}}
[SEG, {SIZ, 2,FLD, 1,DIR, A}}}]
*/
/**
 *
 * @author Miro
 */
public class DataLineProcessor {

    private LineNumberReader reader;
    private List<StringBuilder> dataLines;

    public DataLineProcessor(String inputFileName) throws IOException {
        this(new File(inputFileName));
    }

    public DataLineProcessor(File inputFile) throws IOException {
        this(new FileInputStream(inputFile));
    }

    public DataLineProcessor(InputStream inputStream) {
        this(new BufferedReader(new InputStreamReader(inputStream, Charset.forName("Bg-Mik"))));
    }

    public DataLineProcessor(Reader reader) {
        if(reader instanceof LineNumberReader) {
            this.reader = (LineNumberReader) reader;
        } else {
            this.reader = new LineNumberReader(reader);
        }

        dataLines = new ArrayList<StringBuilder>();
    }

    private void processDataLines() throws IOException {
        StringBuilder dataLine = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            if(line.trim().length() == 0) {
                continue;
            }

            if(line.startsWith(MagicNode.VERSION) || line.startsWith(MagicNode.FILE)) {
                dataLine = new StringBuilder();
                dataLines.add(dataLine);
            }

            dataLine.append(line);
        }
    }

    private FieldNode processFieldNode(StringBuilder line) {
        String value = getAttributeValue(line, MagicNode.DESCRIPTION + "=\"", "\",");
        FieldNode fieldNode = new FieldNode(value);

        value = getAttributeValue(line, MagicNode.PICTURE + "=\"", "\",");
        fieldNode.setPicture(value);

        String pattern = MagicNode.WHOLE_NUMBER + "=";
        int fromIndex = line.indexOf(pattern);
        if(fromIndex >= 0) {
            fieldNode.setNumber(true);
            fromIndex += pattern.length();
            int toIndex = line.indexOf(",", fromIndex);
            fieldNode.setWholeNumberLength(Integer.parseInt(line.substring(fromIndex, toIndex)));
        }

        return fieldNode;
    }

    private FileNode processFileNode(StringBuilder line) {
        removeFirstString(line, MagicNode.FILE + "={");
        removeLastString(line, "}");
        if(!line.toString().startsWith(MagicNode.NAME)) {
            return null;
        }

        String value = getAttributeValue(line, MagicNode.NAME + "=\"", "\",");
        FileNode fileNode = new FileNode(value);

        value = getAttributeValue(line, MagicNode.DESCRIPTION + "=\"", "\",");
        fileNode.setDescription(value);

        String name;
        while((name = getAttributeName(line.toString())) != null) {
            if(MagicNode.FIELD.equals(name) || MagicNode.KEY.equals(name)) {
                removeFirstString(line, name + "=");
                StringBuilder curlyBracketContent = getCurlyBracketContent(line);
                if(MagicNode.FIELD.equals(name)) {
                    FieldNode fieldNode = processFieldNode(curlyBracketContent);
                    fileNode.addFieldNode(fieldNode);
                }
                if(line.length() > 0 && line.charAt(0) == ',') {
                    line.deleteCharAt(0);
                }
            } else {
                value = getAttributeValue(line, name + "=", ",");
                continue;
            }
        }

        return fileNode;
    }

    private StringBuilder getCurlyBracketContent(StringBuilder line) {
        int index = findRightCurlyBracket(line);
        StringBuilder curlyBracketContent = new StringBuilder(line.substring(1, index - 1));
        line.delete(0, index);
        return curlyBracketContent;
    }

    private int findRightCurlyBracket(CharSequence line) {
        int size = line.length();
        int index = 0;
        int curlyBrackets = 0;
        while(index < size) {
            switch(line.charAt(index++)) {
                case '{':
                    curlyBrackets++;
                    break;

                case '}':
                    curlyBrackets--;
                    break;
            }

            if(curlyBrackets == 0) {
                return index;
            }
        }

        return index;
    }

    private String getAttributeName(String line) {
        if(line == null || line.length() == 0) {
            return null;
        }

        int index = line.indexOf("=");
        if(index > 0) {
            return line.substring(0, index);
        }

        return null;
    }

    private String getAttributeValue(StringBuilder line, String beginPattern, String endPattern) {
        removeFirstString(line, beginPattern);
        int index = line.indexOf(endPattern);
        String value = line.substring(0, index);
        removeFirstString(line, value + endPattern);
        return value;
    }

//    public List<T> processData() throws IOException {
    public MagicNode processData() throws IOException {
        MagicNode magicNode = new MagicNode();
        processDataLines();
        for(StringBuilder dataLine : dataLines) {
            String line = dataLine.toString();
            if(!line.startsWith(MagicNode.FILE)) {
                continue;
            }

            FileNode fileNode;
            if((fileNode = processFileNode(dataLine)) != null) {
                magicNode.addFileNode(fileNode);
            }
        }

        return magicNode;
    }

    private void removeFirstString(StringBuilder source, String pattern) {
        source.delete(0, pattern.length());
    }

    private void removeLastString(StringBuilder source, String pattern) {
        int sourceSize = source.length();
        int patternSize = pattern.length();
        int size = sourceSize - patternSize;
        source.setLength(size);
    }

    private List<String> split(String source) {
        if(source == null || source.length() == 0) {
            return Collections.emptyList();
        }

        return Arrays.asList(source.split("=", 2));
    }
}
