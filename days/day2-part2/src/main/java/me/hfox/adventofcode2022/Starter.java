package me.hfox.adventofcode2022;

import me.hfox.adventofcode2022.util.DelayedFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Starter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) throws IOException {
        String directoryName = Paths.get("").toAbsolutePath().getFileName().toString();
        LOGGER.info("Starting Advent of Code 2022 - {}", directoryName);

        File input = new File("input.txt");
        List<String> lines = Files.readAllLines(input.toPath());

        DayTask task = new DayTask(lines);
        long start = System.nanoTime();
        Object result = task.run();
        long end = System.nanoTime();
        LOGGER.info("Result: {}", result);
        LOGGER.info("Took {}ms", DelayedFormatter.format("%.03f", (end - start) / 1000000.0));
    }

}
