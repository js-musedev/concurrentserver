package com.jsbg.concurrentserver.logging;

import com.jsbg.concurrentserver.reporting.Reporter;
import com.jsbg.concurrentserver.reporting.ReportingThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class NumberLogger {
    private static final int port = 4000;
    private static final int maxConnections = 5;
    private static final Logger logger = Logger.getLogger();
    private static final Reporter reporter = Reporter.getReporter();

    private static boolean isRunning = true;

    public static void start() {
        new ReportingThread().start();
        while (isRunning()) {
            try (
                ServerSocket serverSocket = new ServerSocket(port, maxConnections);
            ) {
                serverSocket.setSoTimeout(1);
                Socket clientSocket = serverSocket.accept();
                new NumberLoggingThread(clientSocket).start();
            } catch (SocketTimeoutException e) {
                // This is just so that serverSocket.accept() doesn't block, which would
                // prevent us from shutting down cleanly. An NIO Selector might be a better
                // option here but I'm not really familiar with them yet.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    synchronized public static boolean isRunning() {
        return isRunning;
    }

    synchronized static void stopServer() {
        isRunning = false;
    }
}
