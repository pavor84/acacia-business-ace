/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.impl.acacia.converter;

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
import java.util.List;

/**
 *
 * @author Miro
 */
public class DataProcessor<T> {

    private LineNumberReader reader;
    private Class<T> dataClass;

    public DataProcessor(String inputFileName, Class<T> dataClass) throws IOException {
        this(new File(inputFileName), dataClass);
    }

    public DataProcessor(File inputFile, Class<T> dataClass) throws IOException {
        this(new FileInputStream(inputFile), dataClass);
    }

    public DataProcessor(InputStream inputStream, Class<T> dataClass) {
        this(new BufferedReader(new InputStreamReader(inputStream, Charset.forName("Bg-Mik"))), dataClass);
    }

    public DataProcessor(Reader reader, Class<T> dataClass) {
        if(reader instanceof LineNumberReader) {
            this.reader = (LineNumberReader) reader;
        } else {
            this.reader = new LineNumberReader(reader);
        }
        this.dataClass = dataClass;
    }

    public List<T> processData() throws IOException {
        List<T> data = new ArrayList<T>();
        LineProcessor<T> lineProcessor = new LineProcessor<T>(dataClass);
        String line;
        while((line = reader.readLine()) != null) {
            if(line.trim().length() == 0) {
                continue;
            }

            int lineNumber = reader.getLineNumber();
            try {
                data.add(lineProcessor.processLine(line));
            } catch(Exception ex) {
                throw new IllegalStateException("Error in line number " + lineNumber +
                        " '" + line + "'", ex);
            }
        }

        return data;
    }
}
