package me.hfox.adventofcode2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DayTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayTask.class);

    private final List<String> lines;

    private int[][] heights;
    private int width;
    private int height;

    public DayTask(List<String> lines) {
        this.lines = lines;
    }

    public Object run() {
        List<String> trimmed = lines.stream().filter(v -> !v.equals("")).toList();

        this.height = trimmed.size();
        this.width = lines.stream().mapToInt(String::length).max().orElse(0);

        this.heights = new int[height][width];

        int index = 0;
        for (String line : lines) {
            int[] lineHeights = heights[index];
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                int height = Character.getNumericValue(ch);
                lineHeights[i] = height;
            }

            index++;
        }

        int bestScore = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                bestScore = Math.max(bestScore, getScore(row, col));
            }
        }

        return bestScore;
    }

    public int getScore(int row, int column) {
        return Direction.UP.getVisibleTrees(heights, row, column) *
                Direction.DOWN.getVisibleTrees(heights, row, column) *
                Direction.LEFT.getVisibleTrees(heights, row, column) *
                Direction.RIGHT.getVisibleTrees(heights, row, column);
    }

    private enum Direction {
        UP {
            @Override
            public int getVisibleTrees(int[][] heights, int rowStart, int columnStart) {
                int visible = 0;
                int baseHeight = heights[rowStart][columnStart];
                for (int row = rowStart - 1; row >= 0; row--) {
                    int height = heights[row][columnStart];
                    if (height >= baseHeight) {
                        visible++;
                        break;
                    }

                    visible++;
                }

                return visible;
            }
        },
        DOWN {
            @Override
            public int getVisibleTrees(int[][] heights, int rowStart, int columnStart) {
                int visible = 0;
                int baseHeight = heights[rowStart][columnStart];
                for (int row = rowStart + 1; row < heights.length; row++) {
                    int height = heights[row][columnStart];
                    if (height >= baseHeight) {
                        visible++;
                        break;
                    }

                    visible++;
                }

                return visible;
            }
        },
        LEFT {
            @Override
            public int getVisibleTrees(int[][] heights, int rowStart, int columnStart) {
                int visible = 0;
                int baseHeight = heights[rowStart][columnStart];
                for (int col = columnStart - 1; col >= 0; col--) {
                    int height = heights[rowStart][col];
                    if (height >= baseHeight) {
                        visible++;
                        break;
                    }

                    visible++;
                }

                return visible;
            }
        },
        RIGHT {
            @Override
            public int getVisibleTrees(int[][] heights, int rowStart, int columnStart) {
                int width = heights[rowStart].length;

                int visible = 0;
                int baseHeight = heights[rowStart][columnStart];
                for (int col = columnStart + 1; col < width; col++) {
                    int height = heights[rowStart][col];
                    if (height >= baseHeight) {
                        visible++;
                        break;
                    }

                    visible++;
                }

                return visible;
            }
        };

        public abstract int getVisibleTrees(int[][] heights, int rowStart, int columnStart);

    }

}
