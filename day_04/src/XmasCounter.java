import java.util.List;
import java.util.Map;

public class XmasCounter implements Counter {

    private final char[] inputArray;
    private final int maxInputIndex;
    private final Map<Integer, List<Character>> lineMap;
    private final int minLineIndex = 0;
    private final int maxLineIndex;
    private final int minColumnIndex = 0;
    private final int maxColumnIndex;

    public XmasCounter(Map<Integer, List<Character>> lineMap, String input) {
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

        // Iterate the lines of data
        for (Map.Entry<Integer, List<Character>> entry : lineMap.entrySet()) {
            int inputIndex = 0;
            lineNumber = entry.getKey();
            List<Character> columnData = entry.getValue();

            // Iterate each character looking for first char of input
            for (int columnIndex = 0; columnIndex < columnData.size(); columnIndex++) {
                // Find first char before further searching
                if (inputArray[0] == columnData.get(columnIndex)) {
                    count += getCountOfMatchesStart(lineNumber, columnIndex);
                }
            }
        }

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
        boolean aboveLineExceeded = lineNumber - 1 < minLineIndex;
        boolean belowLineExceeded = lineNumber + 1 > maxLineIndex;
        boolean rightColumnExceeded = columnIndex + 1 > maxColumnIndex;
        boolean leftColumnExceeded = columnIndex - 1 < minColumnIndex;

        // Look above: lineNumber - 1, columnIndex
        if (!aboveLineExceeded) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber - 1).get(columnIndex)) {
                if (inputIndex == maxInputIndex) {
                    count++;
                } else {
                    count += findMatchesGivenStart(lineNumber - 1, columnIndex, inputIndex + 1);
                }
            }
        }

        // Look above and right: lineNumber - 1, columnIndex + 1
        if (!(aboveLineExceeded || rightColumnExceeded)) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber - 1).get(columnIndex + 1)) {
                if (inputIndex == maxInputIndex) {
                    count++;
                } else {
                    count += findMatchesGivenStart(lineNumber - 1, columnIndex + 1, inputIndex + 1);
                }
            }
        }

        // Look right: lineNumber, columnIndex + 1
        if (!rightColumnExceeded) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber).get(columnIndex + 1)) {
                if (inputIndex == maxInputIndex) {
                    count++;
                } else {
                    count += findMatchesGivenStart(lineNumber, columnIndex + 1, inputIndex + 1);
                }
            }
        }

        // Look below and right: lineNumber + 1, columnIndex + 1
        if (!(belowLineExceeded || rightColumnExceeded)) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber + 1).get(columnIndex + 1)) {
                if (inputIndex == maxInputIndex) {
                    count++;
                } else {
                    count += findMatchesGivenStart(lineNumber + 1, columnIndex + 1, inputIndex + 1);
                }
            }
        }

        // Look below: lineNumber + 1, columnIndex
        if (!belowLineExceeded) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber + 1).get(columnIndex)) {
                if (inputIndex == maxInputIndex) {
                    count++;
                } else {
                    count += findMatchesGivenStart(lineNumber + 1, columnIndex, inputIndex + 1);
                }
            }
        }

        // Look below and left: lineNumber + 1, columnIndex - 1
        if (!(belowLineExceeded || leftColumnExceeded)) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber + 1).get(columnIndex - 1)) {
                if (inputIndex == maxInputIndex) {
                    count++;
                } else {
                    count += findMatchesGivenStart(lineNumber + 1, columnIndex - 1, inputIndex + 1);
                }
            }
        }

        // Look left: lineNumber, columnIndex - 1
        if (!leftColumnExceeded) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber).get(columnIndex - 1)) {
                if (inputIndex == maxInputIndex) {
                    count++;
                } else {
                    count += findMatchesGivenStart(lineNumber, columnIndex - 1, inputIndex + 1);
                }
            }
        }

        // Look above and left: lineNumber - 1, columnIndex - 1
        if (!(aboveLineExceeded || leftColumnExceeded)) {
            if (inputArray[inputIndex] == lineMap.get(lineNumber - 1).get(columnIndex - 1)) {
                if (inputIndex == maxInputIndex) {
                    count++;
                } else {
                    count += findMatchesGivenStart(lineNumber - 1, columnIndex - 1, inputIndex + 1);
                }
            }
        }

        return count;
    }
}
