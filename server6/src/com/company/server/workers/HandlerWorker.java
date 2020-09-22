package com.company.server.workers;

import Commands.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class HandlerWorker implements Runnable {

    private final Socket clientSocket;
    private final Logger logger = LoggerFactory.getLogger(HandlerWorker.class);
    private ObjectInputStream in; // поток чтения из сокета

    public HandlerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                in = new ObjectInputStream(clientSocket.getInputStream());
                Answer answer = new Answer(clientSocket);
                Object o = in.readObject();
                answer.toAnswer(o);
            }

        } catch (IOException e) {
            logger.error(clientSocket + " был и сплыл", e);
        } catch (ClassNotFoundException e) {
            logger.error("", e);
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("Потоки не закрываются.", e);
            }
        }
    }
}
