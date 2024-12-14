import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Report {
    private final List<Long> levels;

    public Report(String[] levels) {
        this.levels = Arrays.stream(levels).map(Long::parseLong).collect(Collectors.toList());
    }

    public List<Long> getLevels() {
        return levels;
    }
}
