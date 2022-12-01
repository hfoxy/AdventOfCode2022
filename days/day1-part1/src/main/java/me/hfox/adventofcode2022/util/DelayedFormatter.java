package me.hfox.adventofcode2022.util;

public class DelayedFormatter {
    public static Object format(String format, Object... args) {
        return new Object() {
            @Override
            public String toString() {
                return String.format(format, args);
            }
        };
    }
}
