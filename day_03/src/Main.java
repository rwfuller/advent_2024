import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        long sum = getSumOfMultiplicationValues(args);
        System.out.println("The sum is " + sum);

        sum = getSumOfFilteredMultiplicationValues(args);
        System.out.println("The filtered sum is " + sum);
    }

    private static long getSumOfMultiplicationValues(String[] args) {
        long sum = 0;
        // Read in file data
        var file = new File(args[0]);

        if (file.exists()) {
            try (var reader = new BufferedReader(new FileReader(file))) {
                String line;
                Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
                Pattern numberPattern = Pattern.compile("\\d+");
                while ((line = reader.readLine()) != null) {
                    Matcher matcher = pattern.matcher(line);

                    while (matcher.find()) {
                        String group = matcher.group();
                        Matcher numberMatcher = numberPattern.matcher(group);
                        numberMatcher.find();
                        long num1 = Long.parseLong(numberMatcher.group());
                        numberMatcher.find();
                        long num2 = Long.parseLong(numberMatcher.group());
                        sum += num1 * num2;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                exit(1);
            }
        }

        return sum;
    }

    private static long getSumOfFilteredMultiplicationValues(String[] args) {
        enum ProcessState { ON, OFF }
        ProcessState processState = ProcessState.ON;
        long sum = 0;
        // Read in file data
        var file = new File(args[0]);

        if (file.exists()) {
            try (var reader = new BufferedReader(new FileReader(file))) {
                String line;
                Pattern pattern = Pattern.compile("(mul\\(\\d+,\\d+\\))|(do\\(\\))|(don't\\(\\))");
                Pattern numberPattern = Pattern.compile("\\d+");
                while ((line = reader.readLine()) != null) {
                    Matcher matcher = pattern.matcher(line);

                    while (matcher.find()) {
                        String group = matcher.group();
                        if ("do()".equals(group)) {
                            processState = ProcessState.ON;
                        } else if ("don't()".equals(group)) {
                            processState = ProcessState.OFF;
                        } else if (processState == ProcessState.ON) {
                            Matcher numberMatcher = numberPattern.matcher(group);
                            numberMatcher.find();
                            long num1 = Long.parseLong(numberMatcher.group());
                            numberMatcher.find();
                            long num2 = Long.parseLong(numberMatcher.group());
                            sum += num1 * num2;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                exit(1);
            }
        }

        return sum;
    }
}