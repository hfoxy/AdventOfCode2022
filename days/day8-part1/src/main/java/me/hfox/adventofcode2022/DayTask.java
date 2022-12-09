package me.hfox.adventofcode2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DayTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayTask.class);

    private final List<String> lines;

    public DayTask(List<String> lines) {
        this.lines = lines;
    }

    public Object run() {
        List<String> trimmed = lines.stream().filter(v -> !v.equals("")).toList();

        int count = trimmed.size();
        int width = lines.stream().mapToInt(String::length).max().orElse(0);

        int[][] heights = new int[count][width];
        boolean[][] visible = new boolean[count][width];

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

        for (int i = 0; i < heights.length; i++) {
            LOGGER.info("Row #{}: {}", i, heights[i]);
        }

        checkRC(width, heights, visible, true, true);
        checkRC(width, heights, visible, false, true);
        checkRC(width, heights, visible, true, false);
        checkRC(width, heights, visible, false, false);

        checkCR(width, heights, visible, true, true);
        checkCR(width, heights, visible, false, true);
        checkCR(width, heights, visible, true, false);
        checkCR(width, heights, visible, false, false);

        int visibleCount = 0;
        for (int row = 0; row < count; row++) {
            for (int col = 0; col < width; col++) {
                if (visible[row][col]) {
                    visibleCount++;
                }
            }
        }

        return visibleCount;
    }

    public void checkRC(int width, int[][] heights, boolean[][] visible, boolean rowIncrement, boolean columnIncrement) {
        for (int row = 0; row < heights.length; row++) {
            int minTreeHeight = -1;
            int rowIdx = row;
            if (!rowIncrement) {
                rowIdx = heights.length - 1 - row;
            }

            int[] rowHeights = heights[rowIdx];
            boolean[] rowVisible = visible[rowIdx];

            for (int col = 0; col < width; col++) {
                int colIdx = col;
                if (!columnIncrement) {
                    colIdx = width - 1 - col;
                }

                int height = rowHeights[colIdx];
                if (height > minTreeHeight) {
                    minTreeHeight = height;
                    rowVisible[colIdx] = true;
                    LOGGER.info("{}/{}: Visible (RC, {}, {})", rowIdx, colIdx, rowIncrement, columnIncrement);
                }
            }
        }
    }

    public void checkCR(int width, int[][] heights, boolean[][] visible, boolean rowIncrement, boolean columnIncrement) {
        for (int col = 0; col < width; col++) {
            int minTreeHeight = -1;
            int colIdx = col;
            if (!columnIncrement) {
                colIdx = width - 1 - col;
            }

            for (int row = 0; row < heights.length; row++) {
                int rowIdx = row;
                if (!rowIncrement) {
                    rowIdx = heights.length - 1 - row;
                }

                int rowHeight = heights[rowIdx][colIdx];
                if (rowHeight > minTreeHeight) {
                    minTreeHeight = rowHeight;
                    visible[rowIdx][colIdx] = true;
                    LOGGER.info("{}/{}: Visible (CR, {}, {})", rowIdx, colIdx, rowIncrement, columnIncrement);
                }
            }
        }
    }

}
