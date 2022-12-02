package me.hfox.adventofcode2022;

public enum RockPaperScissors {

    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    private final int score;

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

    public RockPaperScissors getWinningChoice() {
        return switch (this) {
            case SCISSORS -> ROCK;
            case ROCK -> PAPER;
            case PAPER -> SCISSORS;
        };
    }

    public RockPaperScissors getLosingChoice() {
        return switch (this) {
            case ROCK -> SCISSORS;
            case PAPER -> ROCK;
            case SCISSORS -> PAPER;
        };
    }

    public static RockPaperScissors fromChar(char c) {
        return switch (c) {
            case 'A' -> ROCK;
            case 'B' -> PAPER;
            case 'C' -> SCISSORS;
            default -> throw new IllegalArgumentException("Invalid character: " + c);
        };
    }

}
