package me.hfox.adventofcode2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayTask.class);

    private final List<String> lines;

    public DayTask(List<String> lines) {
        this.lines = lines;
    }

    public Object run() {
        for (String line : lines) {
            for (int i = 3; i < line.length(); i++) {
                if (isMessageStart(line, i, 14)) {
                    return i;
                }
            }
        }

        return null;
    }

    public boolean isMessageStart(String line, int startIndex, int lookback) {
        if (lookback > startIndex) {
            return false;
        }

        int endIndex = startIndex - lookback;
        LOGGER.debug("{} -> {}: {}", endIndex, startIndex, line.substring(startIndex - lookback, startIndex));
        Set<Character> check = new HashSet<>();
        for (int i = endIndex; i < startIndex; i++) {
            check.add(line.charAt(i));
        }

        return check.size() == lookback;
    }

}
