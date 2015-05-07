package co.jg.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class to merge properties files. Receive as parameters the file paths for two properties files,
 * merge them into one and write it to disk.
 * @author jgromero
 *
 */
public class Processor {

    public static void main(String[] args) {
        mergeFiles(args[0], args[1]);
    }

    protected static void mergeFiles(String fileName1, String fileName2) {
        PropertiesFileReader fr = new PropertiesFileReader();
        Map<String, String> linesBranch = new LinkedHashMap<String, String>();
        Map<String, String> linesRoot = new LinkedHashMap<String, String>();
        try {
            fr.readFile(fileName1, "UTF-16BE", linesBranch);
            List<String> keysRoot = fr.readFile(fileName2, "UTF-16LE", linesRoot);
            List<String> resultingLines = new ArrayList<String>();
            for (String key : keysRoot) {
                String rootValue = linesRoot.get(key);
                String branchValue = linesBranch.get(key);
                String value = branchValue == null ? rootValue : branchValue;
                if (value == null) {
                    resultingLines.add(key);                	
                } else {
                	resultingLines.add(key + "=" + value);
                }
            }
            //Blank line Added to separate the root keys from the customized keys
            resultingLines.add("");
            for (String key : linesBranch.keySet()) {
                if (!linesRoot.containsKey(key)) {
                    String branchValue = linesBranch.get(key);
                    if (branchValue == null) {
                    	resultingLines.add(key);
                    } else {
                    	resultingLines.add(key + "=" + branchValue);
                    }
                }
            }
            fr.writeFile(resultingLines);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
