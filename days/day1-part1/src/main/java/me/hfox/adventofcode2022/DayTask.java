package me.hfox.adventofcode2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DayTask {

    private final List<String> lines;

    public DayTask(List<String> lines) {
        this.lines = lines;
    }

    public Object run() {
        int maxCalories = 0;

        int calories = 0;
        for (String line : lines) {
            if (line.equals("")) {
                maxCalories = Math.max(maxCalories, calories);
                calories = 0;
                continue;
            }

            calories += Integer.parseInt(line);
        }

        return maxCalories;
    }

}
