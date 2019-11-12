package com.jsbg.concurrentserver.logging;

public class InputValidator {
    private static final int allowedLineLength = 9;

    boolean isValidInput(String line) {
        if (line.length() == allowedLineLength) {
            return isInteger(line);
        }
        return false;
    }

    private boolean isInteger(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
