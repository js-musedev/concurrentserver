package com.jsbg.concurrentserver.logging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class NumberLoggingThread extends Thread {
    private static final Logger logger = Logger.getLogger();
    private static final String terminate = "terminate";

    private Socket clientSocket;
    private InputValidator inputValidator;

    NumberLoggingThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.inputValidator = new InputValidator();
    }

    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String line;
            while (NumberLogger.isRunning() && (line = in.readLine()) != null) {
                shutDownIfInstructedTo(line);
                if (inputValidator.isValidInput(line)) {
                    logger.write(line);
                } else {
                    clientSocket.close();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void shutDownIfInstructedTo(String line) {
        if (line.equalsIgnoreCase(terminate)) {
            NumberLogger.stopServer();
        }
    }

}
