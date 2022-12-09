package me.hfox.adventofcode2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayTask.class);
    private static final boolean DRAW = false;

    private final List<String> lines;

    public DayTask(List<String> lines) {
        this.lines = lines;
    }

    public Object run() {
        Rope rope = new Rope(new Position(0, 0), new Position(0, 0));

        Set<Position> usedPositions = new HashSet<>();
        usedPositions.add(rope.tailPosition);

        for (String line : lines) {
            String[] split = line.split(" ");
            MoveAction action = MoveAction.from(split[0]);
            int moves = Integer.parseInt(split[1]);

            LOGGER.info("----------");
            LOGGER.info("Move: {}", line);
            LOGGER.info("----------");
            for (int i = 0; i < moves; i++) {
                rope = action.moveHead(rope);
                LOGGER.info("Move {}", action);
                if (usedPositions.add(rope.tailPosition)) {
                    LOGGER.info("Added new position: {}/{}", rope.tailPosition.x, rope.tailPosition.y);
                }

                draw(usedPositions, rope);
            }
        }

        LOGGER.info("");
        LOGGER.info("");
        LOGGER.info("");
        LOGGER.info("");
        LOGGER.info("Done");
        LOGGER.info("");
        draw(usedPositions, null);

        return usedPositions.size();
    }

    public void draw(Set<Position> usedPositions, Rope rope) {
        if (!DRAW) {
            return;
        }

        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;

        for (Position pos : usedPositions) {
            minX = Math.min(pos.x, minX);
            maxX = Math.max(pos.x, maxX);
            minY = Math.min(pos.y, minY);
            maxY = Math.max(pos.y, maxY);
        }

        int xDelta = Math.abs(minX);
        int yDelta = Math.abs(minY);
        int width = maxX - minX;
        int height = maxY - minY;
        for (int y = height; y >= 0; y--) {
            StringBuilder builder = new StringBuilder();
            for (int x = 0; x <= width; x++) {
                if (x == 0 && y == 0) {
                    builder.append("s");
                } else if (rope != null && x == rope.headPosition.x && y == rope.headPosition.y) {
                    builder.append("H");
                } else if (rope != null && x == rope.tailPosition.x && y == rope.tailPosition.y) {
                    builder.append("T");
                } else if (usedPositions.contains(new Position(x - xDelta, y - yDelta))) {
                    builder.append("#");
                } else {
                    builder.append(".");
                }
            }

            LOGGER.info("{}", builder);
        }
    }

    private record Rope(Position headPosition, Position tailPosition) {}

    private record Position(int x, int y) {}

    private enum MoveAction {

        UP("U") {
            @Override
            public Rope moveHead(Rope rope) {
                final Position headPosition = rope.headPosition;
                final Position tailPosition = rope.tailPosition;

                Position newTailPosition = new Position(tailPosition.x, tailPosition.y);
                Position newHeadPosition = new Position(headPosition.x, headPosition.y + 1);
                if (tailPosition.y < headPosition.y) {
                    // ...
                    // .H.
                    // ???
                    newTailPosition = new Position(headPosition.x, headPosition.y);
                }

                return new Rope(newHeadPosition, newTailPosition);
            }
        },
        DOWN("D") {
            @Override
            public Rope moveHead(Rope rope) {
                final Position headPosition = rope.headPosition;
                final Position tailPosition = rope.tailPosition;

                Position newTailPosition = new Position(tailPosition.x, tailPosition.y);
                Position newHeadPosition = new Position(headPosition.x, headPosition.y - 1);
                if (tailPosition.y > headPosition.y) {
                    // ???
                    // .H.
                    // ...
                    newTailPosition = new Position(headPosition.x, headPosition.y);
                }

                return new Rope(newHeadPosition, newTailPosition);
            }
        },
        LEFT("L") {
            @Override
            public Rope moveHead(Rope rope) {
                final Position headPosition = rope.headPosition;
                final Position tailPosition = rope.tailPosition;

                Position newTailPosition = new Position(tailPosition.x, tailPosition.y);
                Position newHeadPosition = new Position(headPosition.x - 1, headPosition.y);
                if (tailPosition.x > headPosition.x) {
                    // ..?
                    // .H?
                    // ..?
                    newTailPosition = new Position(headPosition.x, headPosition.y);
                }

                return new Rope(newHeadPosition, newTailPosition);
            }
        },
        RIGHT("R") {
            @Override
            public Rope moveHead(Rope rope) {
                final Position headPosition = rope.headPosition;
                final Position tailPosition = rope.tailPosition;

                Position newTailPosition = new Position(tailPosition.x, tailPosition.y);
                Position newHeadPosition = new Position(headPosition.x + 1, headPosition.y);
                if (tailPosition.x < headPosition.x) {
                    // ?..
                    // ?H.
                    // ?..
                    newTailPosition = new Position(headPosition.x, headPosition.y);
                }

                return new Rope(newHeadPosition, newTailPosition);
            }
        };

        private final String key;

        MoveAction(String key) {
            this.key = key;
        }

        public static MoveAction from(String string) {
            for (MoveAction action : values()) {
                if (action.key.equals(string)) {
                    return action;
                }
            }

            throw new IllegalArgumentException("Unknown move action");
        }

        public abstract Rope moveHead(Rope rope);

    }

}
