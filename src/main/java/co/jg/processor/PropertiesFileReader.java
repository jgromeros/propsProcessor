package co.jg.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;


public class PropertiesFileReader {

    /**
     * Reads the file in the path represented by the fileName parameter and returns a list of Strings in which
     * each item is a line from the file 
     * @param fileName
     * @return
     * @throws IOException
     */
    public Map<String, String> readFile(String fileName, String encoding) throws IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
        Map<String, String> splittedLines = new LinkedHashMap<String, String>();
        String line = null;
        int lineNumber = 0;
        while((line = br.readLine()) != null) {
        	lineNumber++;
        	if (!line.trim().isEmpty()) {
        		if (lineNumber == 1 && line.startsWith("\uFEFF")) {
        	        line = line.substring(1);
        	    }
	            String[] splittedLine = splitLine(line);
	        	if (splittedLine.length != 2) {
	        		splittedLines.put(line, "");
	        	} else {
		            splittedLines.put(splittedLine[0], splittedLine[1]);
	        	}
        	}
        }
        br.close();
        return splittedLines;
    }

    private String[] splitLine(String line) {
        String[] splittedLine = line.split("=", 2);
        return splittedLine;
    }

    public void writeFile(Map<String, String> mergedMap) throws FileNotFoundException,
            UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("merged.properties", "UTF-16BE");
        for (String key : mergedMap.keySet()) {
        	if ("".equals(mergedMap.get(key))) {
        		writer.println(key);
        	} else {
        		writer.println(key + "=" + mergedMap.get(key));
        	}
        }
        writer.close();
    }

}
