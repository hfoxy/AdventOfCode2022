package me.hfox.adventofcode2022;

import java.util.List;

public class DayTask {

    private final List<String> lines;

    public DayTask(List<String> lines) {
        this.lines = lines;
    }

    public Object run() {
        int score = 0;
        for (String line : lines) {
            char[] chars = line.toCharArray();
            if (chars.length == 0) {
                continue;
            }

            if (chars.length != 3) {
                throw new IllegalArgumentException("Invalid line: " + line);
            }

            RockPaperScissors opponent = RockPaperScissors.fromChar(chars[0]);
            RockPaperScissors choice = switch (chars[2]) {
                case 'X' -> opponent.getLosingChoice();
                case 'Y' -> opponent;
                case 'Z' -> opponent.getWinningChoice();
                default -> throw new IllegalArgumentException("Invalid character: " + chars[1]);
            };

            if (choice.beats(opponent)) {
                score += 6;
            } else if (choice == opponent) {
                score += 3;
            }

            score += choice.getScore();
        }

        return score;
    }

}
