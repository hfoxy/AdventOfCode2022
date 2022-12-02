package me.hfox.adventofcode2022;

public enum RockPaperScissors {

    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    private int score;

    RockPaperScissors(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public boolean beats(RockPaperScissors other) {
        return switch (this) {
            case ROCK -> other == SCISSORS;
            case PAPER -> other == ROCK;
            case SCISSORS -> other == PAPER;
        };
    }

    public static RockPaperScissors fromChar(char c) {
        return switch (c) {
            case 'A', 'X' -> ROCK;
            case 'B', 'Y' -> PAPER;
            case 'C', 'Z' -> SCISSORS;
            default -> throw new IllegalArgumentException("Invalid character: " + c);
        };
    }

}
