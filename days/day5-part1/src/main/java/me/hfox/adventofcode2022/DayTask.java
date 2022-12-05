package me.hfox.adventofcode2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DayTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayTask.class);

    private final List<String> lines;

    public DayTask(List<String> lines) {
        this.lines = lines;
    }

    public Object run() {
        Map<Integer, List<Character>> stacks = new HashMap<>();
        List<MoveAction> moveActions = new ArrayList<>();

        boolean stacksComplete = false;
        for (String line : lines) {
            if (!stacksComplete) {
                if (line.equals("") || line.startsWith(" 1")) {
                    stacksComplete = true;
                    continue;
                }

                for (int i = 0; i < line.length(); i++) {
                    char ch = line.charAt(i);
                    if (ch > 64 && ch < 91) {
                        int index = ((i - 1) / 4) + 1;
                        stacks.computeIfAbsent(index, k -> new ArrayList<>()).add(ch);
                    }
                }
            }

            if (stacksComplete) {
                if (!line.startsWith("move")) {
                    continue;
                }

                String[] parts = line.split(" ");
                int count = Integer.parseInt(parts[1]);
                int from = Integer.parseInt(parts[3]);
                int to = Integer.parseInt(parts[5]);
                moveActions.add(new MoveAction(count, from, to));
            }
        }

        for (List<Character> stack : stacks.values()) {
            Collections.reverse(stack);
        }

        LOGGER.info("Stacks: {}", stacks);
        LOGGER.info("Move Actions: {}", moveActions);

        for (MoveAction move : moveActions) {
            List<Character> from = stacks.get(move.from);
            List<Character> to = stacks.get(move.to);
            for (int i = 0; i < move.count; i++) {
                to.add(from.remove(from.size() - 1));
            }
        }

        StringBuilder builder = new StringBuilder();
        for (List<Character> stack : stacks.values()) {
            builder.append(stack.get(stack.size() - 1));
        }

        return builder.toString();
    }

    private record MoveAction(int count, int from, int to) {
    }

}
