package co.jg.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    public Map<String, String> readFile(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        Map<String, String> splittedLines = new LinkedHashMap<String, String>();
        String line = null;
        while((line = br.readLine()) != null) {
            String[] splittedLine = splitLine(line);
            splittedLines.put(splittedLine[0], splittedLine[1]);
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
        PrintWriter writer = new PrintWriter("merged.properties", "UTF-8");
        for (String key : mergedMap.keySet()) {
            writer.println(key + "=" + mergedMap.get(key));
        }
        writer.close();
    }

}
