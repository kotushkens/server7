package com.company.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import com.company.server.workers.ListenerWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Connections {

    private static ObjectInputStream in; // поток чтения из сокета
    private static final Logger logger = LoggerFactory.getLogger(Connections.class);
    public static final int DEFAULT_PORT = 8888;
    private final int port;
    private ServerSocket server; // серверсокет

    private final ForkJoinPool listenersPool = ForkJoinPool.commonPool();

    private static final int LISTENERS_COUNT = 5;

    public Connections(String strPort) {
        int tempPort = DEFAULT_PORT;
        try {
            tempPort = Integer.parseInt(strPort);
        } catch (NumberFormatException ex) {
            logger.info("Ошибка! Неправильный формат порта. Используется порт " + DEFAULT_PORT);
        } finally {
            this.port = tempPort;
        }
    }

    public void run() {
        try {
            server = new ServerSocket(port);
            logger.info("Сервер запущен!");
        } catch (IOException e) {
            logger.error("Порт " + port + " недоступен, выберите другой.", e);
            System.exit(1);
        }

        listenersPool.invoke(new ListenerWorker(server, LISTENERS_COUNT - 1));
    }

}
