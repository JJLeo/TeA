package com.neuromancer42.tea.core.project;

/**
 * Utility for logging messages during Chord's execution.
 *
 * @author Mayur Naik (mhn@cs.stanford.edu)
 */
public class Messages {
    private Messages() { }
    public static void debug(String format, Object... args) {
        if (Config.v().verbose == 0)
            return;
        String msg = String.format(format, args);
        System.out.println("DEBUG: " + msg);
    }
    public static void log(String format, Object... args) {
        String msg = String.format(format, args);
        System.out.println("LOG: " + msg);
    }
    public static void warn(String format, Object... args) {
        String msg = String.format(format, args);
        System.err.println("WARN: " + msg);
    }
    public static void error(String format, Object... args) {
        String msg = String.format(format, args);
        System.err.println("ERROR: " + msg);
    }
    public static void fatal(String format, Object... args) {
        String msg = String.format(format, args);
        Error ex = new Error("FATAL: " + msg);
        ex.printStackTrace();
        System.exit(1);
    }
    public static void fatal(Throwable ex) {
        ex.printStackTrace();
        System.exit(1);
    }
}

