package co.jg.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class PropertiesFileReader {

    /**
     * Reads the file in the path represented by the fileName parameter and returns a list of Strings in which
     * each item is a line from the file 
     * @param fileName
     * @return
     * @throws IOException
     */
    public List<String> readFile(String fileName, String encoding, Map<String, String> splittedLines) throws IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
        List<String> keys = new ArrayList<String>();
        String line = null;
        int lineNumber = 0;
        while((line = br.readLine()) != null) {
        	lineNumber++;
    		if (lineNumber == 1 && line.startsWith("\uFEFF")) {
    	        line = line.substring(1);
    	    }
            String[] splittedLine = splitLine(line);
        	if (splittedLine.length != 2) {
        		splittedLines.put(line, null);
        	} else {
	            splittedLines.put(splittedLine[0], splittedLine[1]);
        	}
        	keys.add(splittedLine[0]);
        }
        br.close();
        return keys;
    }

    private String[] splitLine(String line) {
        String[] splittedLine = line.split("=", 2);
        return splittedLine;
    }

    public void writeFile(List<String> resultingLines) throws FileNotFoundException,
            UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("merged.properties", "UTF-16BE");
        int linesWritten = 0;
        for (String line : resultingLines) {
        	if (linesWritten == 0) {
        		writer.println("\uFEFF" + line);
        	} else {
        		writer.println(line);
        	}
    		linesWritten++;
        }
        writer.close();
    }

}
