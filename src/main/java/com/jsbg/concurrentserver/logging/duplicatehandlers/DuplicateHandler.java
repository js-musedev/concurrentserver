package com.jsbg.concurrentserver.logging.duplicatehandlers;

public interface DuplicateHandler {
    boolean isDuplicate(int n);
}
