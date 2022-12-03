package me.hfox.adventofcode2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.IntStream;

public class DayTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayTask.class);

    private final List<String> lines;

    public DayTask(List<String> lines) {
        this.lines = lines;
    }

    public Object run() {
        return IntStream.range(0, lines.size() / 3).boxed().map(i -> {
            String line1 = lines.get(i * 3);
            String line2 = lines.get(i * 3 + 1);
            String line3 = lines.get(i * 3 + 2);

            return Arrays.asList(line1, line2, line3);
        }).map(list -> {
            List<List<Character>> characters = new ArrayList<>();
            for (String line : list) {
                List<Character> chars = new ArrayList<>();
                for (char ch : line.toCharArray()) {
                    chars.add(ch);
                }

                characters.add(chars);
            }

            return characters;
        }).map(chars -> {
            boolean first = true;
            Set<Character> set = new HashSet<>();
            for (List<Character> backpack : chars) {
                if (first) {
                    set.addAll(backpack);
                    first = false;
                } else {
                    set.retainAll(backpack);
                }
            }

            if (set.size() != 1) {
                throw new IllegalStateException("Invalid set size: " + set.size());
            }

            return set.iterator().next();
        }).mapToInt(this::getScore).sum();
    }

    private int getScore(char ch) {
        int score;
        if (Character.isUpperCase(ch)) {
            score = ch - 'A' + 1 + 26;
        } else {
            score = ch - 'a' + 1;
        }

        return score;
    }

}
