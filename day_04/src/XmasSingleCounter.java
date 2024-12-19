import java.util.List;
import java.util.Map;

public class XmasSingleCounter implements Counter {

    private final char[] inputArray;
    private final int maxInputIndex;
    private final Map<Integer, List<Character>> lineMap;
    private final int minLineIndex = 0;
    private final int maxLineIndex;
    private final int minColumnIndex = 0;
    private final int maxColumnIndex;

    public XmasSingleCounter(Map<Integer, List<Character>> lineMap, String input) {
        this.lineMap = lineMap;
        this.maxLineIndex = this.lineMap.size() - 1;
        // Assumes all list values are same size
        this.maxColumnIndex = this.lineMap.get(0).size() - 1;
        this.inputArray = input.toCharArray();
        this.maxInputIndex = input.length() - 1;
    }

    @Override
    public int countInstances() {
        int count = 0;

        // Line stats
        int lineNumber = 0;

        int totalXFound = 0;

        // Iterate the lines of data
        for (Map.Entry<Integer, List<Character>> entry : lineMap.entrySet()) {
            int inputIndex = 0;
            lineNumber = entry.getKey();
            List<Character> columnData = entry.getValue();
            int xFound = 0;

            // Iterate each character looking for first char of input
            for (int columnIndex = 0; columnIndex < columnData.size(); columnIndex++) {
                // Find first char before further searching
                if (inputArray[0] == columnData.get(columnIndex)) {
                    xFound++;
                    count += getCountOfMatchesStart(lineNumber, columnIndex);
                }
            }

            totalXFound += xFound;
        }

        System.out.println("Total X's found: " + totalXFound);

        return count;
    }

    private int getCountOfMatchesStart(int lineNumber, int columnIndex) {
        return findMatchesGivenStart(lineNumber, columnIndex, 1);
    }

    private int findMatchesGivenStart(int lineNumber, int columnIndex, int inputIndex) {
        int count = 0;

        if (inputIndex > maxInputIndex) {
            return 0;
        }

        // Check constraints
        boolean aboveLineExceeded = lineNumber - 3 < minLineIndex;
        boolean belowLineExceeded = lineNumber + 3 > maxLineIndex;
        boolean rightColumnExceeded = columnIndex + 3 > maxColumnIndex;
        boolean leftColumnExceeded = columnIndex - 3 < minColumnIndex;
        boolean aboveRightDiagonalExceeded = aboveLineExceeded || rightColumnExceeded;
        boolean belowRightDiagonalExceeded = belowLineExceeded || rightColumnExceeded;
        boolean belowLeftDiagonalExceeded = belowLineExceeded || leftColumnExceeded;
        boolean aboveLeftDiagonalExceeded = aboveLineExceeded || leftColumnExceeded;

        // Look above: lineNumber - 1, columnIndex
        if (!aboveLineExceeded) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber - 1).get(columnIndex) &&
                    inputArray[inputIndex + 1] == lineMap.get(lineNumber - 2).get(columnIndex) &&
                    inputArray[inputIndex + 2] == lineMap.get(lineNumber - 3).get(columnIndex)) {
                count++;
            }
        }

        // Look above and right: lineNumber - 1, columnIndex + 1
        if (!aboveRightDiagonalExceeded) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber - 1).get(columnIndex + 1) &&
                    inputArray[inputIndex + 1] == lineMap.get(lineNumber - 2).get(columnIndex + 2) &&
                    inputArray[inputIndex + 2] == lineMap.get(lineNumber - 3).get(columnIndex + 3)) {
                count++;
            }
        }

        // Look right: lineNumber, columnIndex + 1
        if (!rightColumnExceeded) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber).get(columnIndex + 1) &&
                    inputArray[inputIndex + 1] == lineMap.get(lineNumber).get(columnIndex + 2) &&
                    inputArray[inputIndex + 2] == lineMap.get(lineNumber).get(columnIndex + 3)) {
                count++;
            }
        }

        // Look below and right: lineNumber + 1, columnIndex + 1
        if (!belowRightDiagonalExceeded) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber + 1).get(columnIndex + 1) &&
                    inputArray[inputIndex + 1] == lineMap.get(lineNumber + 2).get(columnIndex + 2) &&
                    inputArray[inputIndex + 2] == lineMap.get(lineNumber + 3).get(columnIndex + 3)) {
                count++;
            }
        }

        // Look below: lineNumber + 1, columnIndex
        if (!belowLineExceeded) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber + 1).get(columnIndex) &&
                    inputArray[inputIndex + 1] == lineMap.get(lineNumber + 2).get(columnIndex) &&
                    inputArray[inputIndex + 2] == lineMap.get(lineNumber + 3).get(columnIndex)) {
                count++;
            }
        }

        // Look below and left: lineNumber + 1, columnIndex - 1
        if (!belowLeftDiagonalExceeded) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber + 1).get(columnIndex - 1) &&
                    inputArray[inputIndex + 1] == lineMap.get(lineNumber + 2).get(columnIndex - 2) &&
                    inputArray[inputIndex + 2] == lineMap.get(lineNumber + 3).get(columnIndex - 3)) {
                count++;
            }
        }

        // Look left: lineNumber, columnIndex - 1
        if (!leftColumnExceeded) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber).get(columnIndex - 1) &&
                    inputArray[inputIndex + 1] == lineMap.get(lineNumber).get(columnIndex - 2) &&
                    inputArray[inputIndex + 2] == lineMap.get(lineNumber).get(columnIndex - 3)) {
                count++;
            }
        }

        // Look above and left: lineNumber - 1, columnIndex - 1
        if (!aboveLeftDiagonalExceeded) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber - 1).get(columnIndex - 1)
                    && inputArray[inputIndex + 1] == lineMap.get(lineNumber - 2).get(columnIndex - 2)
                    && inputArray[inputIndex + 2] == lineMap.get(lineNumber - 3).get(columnIndex - 3)) {
                count++;
            }
        }

        return count;
    }
}
