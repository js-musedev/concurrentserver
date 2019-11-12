package com.jsbg.concurrentserver.reporting;

import java.util.concurrent.atomic.AtomicInteger;

public class Reporter {
    private static Reporter reporter = null;

    private AtomicInteger uniqueCount = new AtomicInteger(0);
    private AtomicInteger uniqueTotal = new AtomicInteger(0);
    private AtomicInteger duplicateCount = new AtomicInteger(0);

    public void addUniqueCount() {
        uniqueCount.incrementAndGet();
        uniqueTotal.incrementAndGet();
    }

    public void reportDuplicate() {
        duplicateCount.incrementAndGet();
    }

    void report() {
        System.out.println("Received " + uniqueCount + " unique number(s), " +
                duplicateCount + " duplicate(s). Unique total: " + uniqueTotal);
        resetCounts();
    }

    private void resetCounts() {
        uniqueCount = new AtomicInteger(0);
        duplicateCount = new AtomicInteger(0);
    }

    synchronized public static Reporter getReporter() {
        if (reporter == null) {
            reporter = new Reporter();
        }
        return reporter;
    }
}
