package me.hfox.adventofcode2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DayTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayTask.class);

    private final List<String> lines;

    public DayTask(List<String> lines) {
        this.lines = lines;
    }

    public Object run() {
        List<Integer> totalCalories = new ArrayList<>();

        int calories = 0;
        for (String line : lines) {
            if (line.equals("")) {
                totalCalories.add(calories);
                calories = 0;
                continue;
            }

            calories += Integer.parseInt(line);
        }

        return totalCalories.stream().sorted(Collections.reverseOrder()).mapToInt(v -> v).limit(3).sum();
    }

}
