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
        Map<String, DirectoryItem> items = new HashMap<>();

        StringBuilder directory = new StringBuilder("/");
        for (String line : lines) {
            if (line.startsWith("$")) {
                if (line.startsWith("$ cd ")) {
                    String cd = line.substring(5);
                    if (cd.startsWith("/")) {
                        directory = new StringBuilder(cd);
                    } else if (cd.startsWith("..")) {
                        int index = directory.lastIndexOf("/");
                        directory = new StringBuilder(directory.substring(0, index));
                    } else {
                        if (directory.toString().endsWith("/")) {
                            directory.append(cd);
                        } else {
                            directory.append("/").append(cd);
                        }
                    }

                    if (directory.toString().endsWith("/")) {
                        directory.deleteCharAt(directory.length() - 1);
                    }

                    // LOGGER.info("Directory: {}", directory);
                    DirectoryItem item = items.computeIfAbsent(directory.toString(), k -> new DirectoryItem());

                    String current = directory.toString();
                    if (!current.equals("/") && current.contains("/")) {
                        int index = directory.lastIndexOf("/");
                        String parent = directory.substring(0, index);
                        DirectoryItem parentItem = items.computeIfAbsent(parent, k -> new DirectoryItem());
                        parentItem.addItem(item);
                    }
                }
            } else if (!line.startsWith("dir")) {
                // LOGGER.info("File: {}", line);
                int splitIndex = line.indexOf(" ");

                int size = Integer.parseInt(line.substring(0, splitIndex));
                String name = line.substring(splitIndex + 1);
                DirectoryItem item = items.computeIfAbsent(directory.toString(), k -> new DirectoryItem());
                item.addItem(new DirectoryItem(size));

                LOGGER.info("Adding {} to {} ({}/{})", name, directory, directory, name);
            }
        }

        return items.values().stream().mapToInt(DirectoryItem::getTotalSize).filter(i -> i <= 100000).sum();
    }

    private static class DirectoryItem {

        private final boolean directory;
        private final int size;
        private final Set<DirectoryItem> children;

        public DirectoryItem() {
            this(true, 0);
        }

        public DirectoryItem(int size) {
            this(false, size);
        }

        private DirectoryItem(boolean directory, int size) {
            this.directory = directory;
            this.size = size;
            this.children = new HashSet<>();
        }

        public boolean isDirectory() {
            return directory;
        }

        public int getSize() {
            return size;
        }

        public int getTotalSize() {
            int total = size;
            for (DirectoryItem child : children) {
                total += child.getTotalSize();
            }

            return total;
        }

        public void addItem(DirectoryItem item) {
            children.add(item);
        }

    }

}
