package co.jg.processor;

import java.io.IOException;
import java.util.LinkedHashMap;
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
        try {
            Map<String, String> linesBranch = fr.readFile(fileName1);
            Map<String, String> linesRoot = fr.readFile(fileName2);
            Map<String, String> mergedMap = new LinkedHashMap<String, String>();
            for (String key : linesRoot.keySet()) {
                String rootValue = linesRoot.get(key);
                String branchValue = linesBranch.get(key);
                mergedMap.put(key, branchValue == null ? rootValue : branchValue);
            }
            for (String key : linesBranch.keySet()) {
                if (!mergedMap.containsKey(key)) {
                    String branchValue = linesBranch.get(key);
                    mergedMap.put(key, branchValue);                    
                }
            }
            fr.writeFile(mergedMap);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
