package me.hfox.adventofcode2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayTask {

    private final List<String> lines;

    public DayTask(List<String> lines) {
        this.lines = lines;
    }

    public Object run() {
        for (String line : lines) {
            for (int i = 3; i < line.length(); i++) {
                Set<Character> check = new HashSet<>();
                check.add(line.charAt(i - 3));
                check.add(line.charAt(i - 2));
                check.add(line.charAt(i - 1));
                check.add(line.charAt(i));

                if (check.size() == 4) {
                    return i + 1;
                }
            }
        }

        return null;
    }

}
