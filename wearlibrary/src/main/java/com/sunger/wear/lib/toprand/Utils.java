package com.sunger.wear.lib.toprand;

/**
 * Created by sunger on 15/6/13.
 */
public class Utils {
    private Utils() {
    }

    public static void asserts(final boolean expression, final String failedMessage) {
        if (!expression) {
            throw new AssertionError(failedMessage);
        }
    }
    public static <T> T notNull(final T argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " should not be null!");
        }
        return argument;
    }
}
