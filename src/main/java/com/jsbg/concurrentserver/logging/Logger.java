package com.jsbg.concurrentserver.logging;

import com.jsbg.concurrentserver.logging.duplicatehandlers.DuplicateHandler;
import com.jsbg.concurrentserver.logging.duplicatehandlers.InMemoryDuplicateHandler;
import com.jsbg.concurrentserver.reporting.Reporter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Logger {
    private static final String fileName = "numbers.log";
    private static final Reporter reporter = Reporter.getReporter();
    private static Logger logger = null;

    private BufferedWriter writer;
    private DuplicateHandler duplicateHandler;

    private Logger() {
        createOrOverwriteFile();
        this.duplicateHandler = new InMemoryDuplicateHandler();
    }

    private void createOrOverwriteFile() {
        try {
            writer = Files.newBufferedWriter(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void write(String line) throws IOException {
        int n = Integer.parseInt(line);
        if (!duplicateHandler.isDuplicate(n)) {
            synchronized (this) {
                writer.write(String.format(line + "%n"));
                writer.flush();
            }
            reporter.addUniqueCount();
        } else {
            reporter.reportDuplicate();
        }
    }

    public boolean contains(int n) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                int current = Integer.parseInt(line);
                if (current == n) {
                    //reader.close();
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized static Logger getLogger() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

}
