package me.hfox.adventofcode2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayTask.class);
    private static final boolean DRAW = false;

    private final List<String> lines;

    private final Set<Position> usedPositions = new HashSet<>();

    public DayTask(List<String> lines) {
        this.lines = lines;
    }

    public Object run() {
        List<Position> knots = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            knots.add(new Position(0, 0));
        }

        Rope rope = new Rope(knots);
        addPosition(rope);

        for (String line : lines) {
            String[] split = line.split(" ");
            MoveAction action = MoveAction.from(split[0]);
            int moves = Integer.parseInt(split[1]);

            LOGGER.info("----------");
            LOGGER.info("Move: {}", line);
            LOGGER.info("----------");
            for (int i = 0; i < moves; i++) {
                Position previous = null;
                MoveAction previousAction = action;

                for (Position position : rope.knots) {
                    if (previous == null) {
                        previous = position;
                        continue;
                    }

                    RopePair ropePair = new RopePair(previous, position);
                    ropePair = previousAction.moveHead(ropePair);
                    LOGGER.info("Move {}", action);
                    if (usedPositions.add(ropePair.tailPosition)) {
                        LOGGER.info("Added new position: {}/{}", ropePair.tailPosition.x, ropePair.tailPosition.y);
                    }

                    draw(usedPositions, ropePair);
                    if (ropePair.tailPosition.x > position.x) {
                        previousAction = MoveAction.LEFT;
                    } else if (ropePair.tailPosition.x < position.x) {
                        previousAction = MoveAction.RIGHT;
                    } else if (ropePair.tailPosition.y > position.y) {
                        previousAction = MoveAction.UP;
                    } else if (ropePair.tailPosition.y < position.y) {
                        previousAction = MoveAction.DOWN;
                    }
                }
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

    public void addPosition(Rope rope) {
        usedPositions.add(rope.knots.get(9));
    }

    public void draw(Set<Position> usedPositions, RopePair ropePair) {
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
                } else if (ropePair != null && x == ropePair.headPosition.x && y == ropePair.headPosition.y) {
                    builder.append("H");
                } else if (ropePair != null && x == ropePair.tailPosition.x && y == ropePair.tailPosition.y) {
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

    private record Rope(List<Position> knots) {}

    private record RopePair(Position headPosition, Position tailPosition) {}

    private record Position(int x, int y) {}

    private enum MoveAction {

        UP("U") {
            @Override
            public RopePair moveHead(RopePair ropePair) {
                final Position headPosition = ropePair.headPosition;
                final Position tailPosition = ropePair.tailPosition;

                Position newTailPosition = new Position(tailPosition.x, tailPosition.y);
                Position newHeadPosition = new Position(headPosition.x, headPosition.y + 1);
                if (tailPosition.y < headPosition.y) {
                    // ...
                    // .H.
                    // ???
                    newTailPosition = new Position(headPosition.x, headPosition.y);
                }

                return new RopePair(newHeadPosition, newTailPosition);
            }
        },
        DOWN("D") {
            @Override
            public RopePair moveHead(RopePair ropePair) {
                final Position headPosition = ropePair.headPosition;
                final Position tailPosition = ropePair.tailPosition;

                Position newTailPosition = new Position(tailPosition.x, tailPosition.y);
                Position newHeadPosition = new Position(headPosition.x, headPosition.y - 1);
                if (tailPosition.y > headPosition.y) {
                    // ???
                    // .H.
                    // ...
                    newTailPosition = new Position(headPosition.x, headPosition.y);
                }

                return new RopePair(newHeadPosition, newTailPosition);
            }
        },
        LEFT("L") {
            @Override
            public RopePair moveHead(RopePair ropePair) {
                final Position headPosition = ropePair.headPosition;
                final Position tailPosition = ropePair.tailPosition;

                Position newTailPosition = new Position(tailPosition.x, tailPosition.y);
                Position newHeadPosition = new Position(headPosition.x - 1, headPosition.y);
                if (tailPosition.x > headPosition.x) {
                    // ..?
                    // .H?
                    // ..?
                    newTailPosition = new Position(headPosition.x, headPosition.y);
                }

                return new RopePair(newHeadPosition, newTailPosition);
            }
        },
        RIGHT("R") {
            @Override
            public RopePair moveHead(RopePair ropePair) {
                final Position headPosition = ropePair.headPosition;
                final Position tailPosition = ropePair.tailPosition;

                Position newTailPosition = new Position(tailPosition.x, tailPosition.y);
                Position newHeadPosition = new Position(headPosition.x + 1, headPosition.y);
                if (tailPosition.x < headPosition.x) {
                    // ?..
                    // ?H.
                    // ?..
                    newTailPosition = new Position(headPosition.x, headPosition.y);
                }

                return new RopePair(newHeadPosition, newTailPosition);
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

        public abstract RopePair moveHead(RopePair ropePair);

    }

}
