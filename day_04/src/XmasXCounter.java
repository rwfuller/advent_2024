import java.util.List;
import java.util.Map;

public class XmasXCounter implements Counter {

    private final char[] inputArray;
    private final int maxInputIndex;
    private final Map<Integer, List<Character>> lineMap;
    private final int minLineIndex = 0;
    private final int maxLineIndex;
    private final int minColumnIndex = 0;
    private final int maxColumnIndex;

    public XmasXCounter(Map<Integer, List<Character>> lineMap, String input) {
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

        int totalMiddleFound = 0;

        // Iterate the lines of data
        for (Map.Entry<Integer, List<Character>> entry : lineMap.entrySet()) {
            int inputIndex = 0;
            lineNumber = entry.getKey();
            List<Character> columnData = entry.getValue();
            int middleFound = 0;

            // Iterate each character looking for middle char of input
            for (int columnIndex = 0; columnIndex < columnData.size(); columnIndex++) {
                // Find middle char before further searching
                if (inputArray[1] == columnData.get(columnIndex)) {
                    middleFound++;
                    count += getCountOfMatchesStart(lineNumber, columnIndex);
                }
            }

            totalMiddleFound += middleFound;
        }

        System.out.println("Total Middle chars found: " + totalMiddleFound);

        return count;
    }

    private int getCountOfMatchesStart(int lineNumber, int columnIndex) {
        return findMatchesGivenStart(lineNumber, columnIndex);
    }

    private int findMatchesGivenStart(int lineNumber, int columnIndex) {
        int count = 0;

        // Check constraints
        boolean aboveLineExceeded = lineNumber - 1 < minLineIndex;
        boolean belowLineExceeded = lineNumber + 1 > maxLineIndex;
        boolean rightColumnExceeded = columnIndex + 1 > maxColumnIndex;
        boolean leftColumnExceeded = columnIndex - 1 < minColumnIndex;
        boolean aboveRightDiagonalExceeded = aboveLineExceeded || rightColumnExceeded;
        boolean belowRightDiagonalExceeded = belowLineExceeded || rightColumnExceeded;
        boolean belowLeftDiagonalExceeded = belowLineExceeded || leftColumnExceeded;
        boolean aboveLeftDiagonalExceeded = aboveLineExceeded || leftColumnExceeded;

        // Look above: lineNumber - 1, columnIndex
        if (!(aboveRightDiagonalExceeded || aboveLeftDiagonalExceeded || belowRightDiagonalExceeded || belowLeftDiagonalExceeded)) {
            if (((inputArray[0] == lineMap.get(lineNumber - 1).get(columnIndex - 1) && inputArray[2] == lineMap.get(lineNumber + 1).get(columnIndex + 1))
                    || (inputArray[2] == lineMap.get(lineNumber - 1).get(columnIndex - 1) && inputArray[0] == lineMap.get(lineNumber + 1).get(columnIndex + 1)))
                    && ((inputArray[0] == lineMap.get(lineNumber - 1).get(columnIndex + 1) && inputArray[2] == lineMap.get(lineNumber + 1).get(columnIndex - 1))
                    || (inputArray[2] == lineMap.get(lineNumber - 1).get(columnIndex + 1) && inputArray[0] == lineMap.get(lineNumber + 1).get(columnIndex - 1)))) {
                System.out.println("Found X at point (" + columnIndex + ", " + lineNumber + ")");
                count++;
            }
        }

        return count;
    }
}
