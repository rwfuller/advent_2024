public class ReportChecker implements Checker<Report> {

    @Override
    public boolean isValid(Report item) {
        boolean valid = true;
        var reportLevelDirection = LevelDirection.NONE;

        Long previousLevel = null;
        for (Long level : item.getLevels()) {
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
