package com.jsbg.concurrentserver.logging.duplicatehandlers;

import java.util.HashSet;
import java.util.Set;

public class InMemoryDuplicateHandler implements DuplicateHandler {
    volatile private Set<Integer> seen = new HashSet<>();

    @Override
    public boolean isDuplicate(int n) {
        if (seen.contains(n)) {
            return true;
        }
        seen.add(n);
        return false;
    }
}
