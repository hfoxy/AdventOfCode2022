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
        int duplicateScore = 0;
        for (String line : lines) {
            char[] chars = line.toCharArray();

            Set<Character> items = new HashSet<>();
            Set<Character> duplicates = new HashSet<>();

            int index = 0;
            for (char ch : chars) {
                if (index > (chars.length / 2) - 1) {
                    if (items.contains(ch)) {
                        duplicates.add(ch);
                    }
                } else {
                    items.add(ch);
                }

                index++;
            }

            for (Character duplicate : duplicates) {
                int score;
                if (Character.isUpperCase(duplicate)) {
                    score = duplicate - 'A' + 1 + 26;
                } else {
                    score = duplicate - 'a' + 1;
                }

                duplicateScore += score;
            }
        }

        return duplicateScore;
    }

}
