package com.company.server.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class ListenerWorker extends RecursiveAction {

    private Socket clientSocket; // сокет для общения
    private ServerSocket server; // серверсокет
    private int depth;

    public static final int MAX_DEPTH = 5;

    private final Logger logger = LoggerFactory.getLogger(ListenerWorker.class);

    private final ExecutorService handlersPool = Executors.newCachedThreadPool();



    public ListenerWorker(ServerSocket serverSocket, int depth) {
        this.server = serverSocket;
        this.depth = depth;
    }



    @Override
    public void compute() {
        if (depth > 0) {
            new ListenerWorker(server, depth - 1).fork();
        }
        logger.info("Listening... Ready to catch client");
        try {
            while (true) {
                clientSocket = server.accept();
                logger.info("I've been waiting for you to come': " + clientSocket);
                handlersPool.execute(new HandlerWorker(clientSocket));
            }
        } catch (IOException e) {
            logger.error("Ошибка (IO) при подключении клиента", e);
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
