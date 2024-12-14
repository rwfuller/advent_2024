import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        // Read in file data
        var file = new File(args[0]);
        var reports = new ArrayList<Report>();

        if (file.exists()) {
            try (var reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split(" ");
                    reports.add(new Report(split));
                }
            } catch (Exception e) {
                e.printStackTrace();
                exit(1);
            }
        }

        // Check count of valid reports
        var reportChecker = new ReportChecker();
        var successfulReports = reports.stream().filter(reportChecker::isValid).count();
        System.out.println("Successful reports: " + successfulReports);

        // Check count of valid reports with problem dampener
        var reportCheckerWithDampener = new ReportCheckerWithDampener();
        successfulReports = reports.stream().filter(reportCheckerWithDampener::isValid).count();
        System.out.println("Successful reports with problem dampener: " + successfulReports);
    }
}