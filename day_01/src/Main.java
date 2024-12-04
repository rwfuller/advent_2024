import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        // Read in file data
        var file = new File(args[0]);
        var numList1 = new ArrayList<Long>();
        var numList2 = new ArrayList<Long>();
        long sum = 0;

        if (file.exists()) {
            try (var reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split("   ");
                    if (split.length == 2) {
                        numList1.add(Long.parseLong(split[0]));
                        numList2.add(Long.parseLong(split[1]));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                exit(1);
            }
        }

        // Sort data
        Collections.sort(numList1);
        Collections.sort(numList2);

        // Calculate data
        if (numList1.size() == numList2.size()) {
            for (int i = 0; i < numList1.size(); i++) {
                sum += Math.abs(numList1.get(i) - numList2.get(i));
            }
        }

        System.out.println("The sum is " + sum);

        // Calculate similarity sum
        long similaritySum = 0;
        // Used to cache previous values
        var similarityCache = new HashMap<Long, Long>();
        for (Long num1 : numList1) {
            // Check cache first
            if (!similarityCache.containsKey(num1)) {
                long count = numList2.stream().filter(num -> num.equals(num1)).count();
                similarityCache.put(num1, count);
            }

            similaritySum += num1 * similarityCache.get(num1);
        }

        System.out.println("The similarity sum is " + similaritySum);
    }
}