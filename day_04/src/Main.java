import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        // Read in data
        var lineMap = loadData(args);
        var processor = new XmasSingleCounter(lineMap, "XMAS");

        var count = processor.countInstances();
        System.out.println("Number of matches: " + count);

        var xProcessor = new XmasXCounter(lineMap, "MAS");
        count = xProcessor.countInstances();

        System.out.println("Number of X matches: " + count);
    }

    private static Map<Integer, List<Character>> loadData(String[] args) {
        Map<Integer, List<Character>> lineMap = new HashMap<>();
        var file = new File(args[0]);

        if (file.exists()) {
            try (var reader = new BufferedReader(new FileReader(file))) {
                String line;
                int lineNumber = 0;
                while ((line = reader.readLine()) != null) {
                    List<Character> characterList = new ArrayList<>();
                    // Go through line and copy characters to list
                    for (char c : line.toCharArray()) {
                        characterList.add(c);
                    }

                    lineMap.put(lineNumber, characterList);
                    lineNumber++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                exit(1);
            }
        }

        return lineMap;
    }
}