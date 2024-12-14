import java.util.LinkedList;
import java.util.List;

public class ReportCheckerWithDampener implements Checker<Report> {

    @Override
    public boolean isValid(Report item) {
        return checkValidity(item);
    }

    private boolean checkValidity(Report report) {
        int totalItems = report.getLevels().size();
        boolean valid = checkValidity(report.getLevels());

        if (!valid) {
            int currentItem = 0;

            while (!valid && currentItem < totalItems) {
                var levels = getLevelsWithOneItemRemoved(report.getLevels(), currentItem);
                valid = checkValidity(levels);
                currentItem++;
            }
        }

        return valid;
    }

    private List<Long> getLevelsWithOneItemRemoved(List<Long> levels, int itemIndex) {
        List<Long> levelsCopy = new LinkedList<>();

        for (int i = 0; i < levels.size(); i++) {
            if (i != itemIndex) {
                levelsCopy.add(levels.get(i));
            }
        }

        return levelsCopy;
    }

    private boolean checkValidity(List<Long> levels) {
        boolean valid = true;
        var reportLevelDirection = LevelDirection.NONE;

        Long previousLevel = null;
        for (Long level : levels) {
            // Set initial level
            if (previousLevel == null) {
                previousLevel = level;
                continue;
            }

            // Calculate direction
            var currentLevelDirection = LevelDirection.NONE;
            if (level > previousLevel) {
                currentLevelDirection = LevelDirection.INCREASING;
            } else if (level < previousLevel) {
                currentLevelDirection = LevelDirection.DECREASING;
            } else {
                // not valid if equal
                valid = false;
                break;
            }

            // Check previous values
            if (reportLevelDirection == LevelDirection.NONE) {
                reportLevelDirection = currentLevelDirection;
            } else if (reportLevelDirection != currentLevelDirection) {
                valid = false;
                break;
            }

            // Check max difference
            if (Math.abs(level - previousLevel) > 3) {
                valid = false;
                break;
            }

            previousLevel = level;
        }

        return valid;
    }
}
