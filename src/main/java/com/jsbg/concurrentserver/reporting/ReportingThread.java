package com.jsbg.concurrentserver.reporting;

import com.jsbg.concurrentserver.logging.NumberLogger;

public class ReportingThread extends Thread {
    private static final Reporter reporter = Reporter.getReporter();
    private static final long tenSeconds = 10 * 1000;

    public void run() {
        while (NumberLogger.isRunning()) {
            reporter.report();
            sleepTenSeconds();
        }
        reporter.report();
    }

    private void sleepTenSeconds() {
        try {
            Thread.sleep(tenSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
