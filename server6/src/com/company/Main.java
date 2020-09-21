package com.company;

import com.company.server.Connections;
import com.company.server.workers.ListenerWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;

public class Main {


    public static void main(String[] args) {
//        Runtime.getRuntime().addShutdownHook(new Thread(JsonUtils::saveCollectionToJson));
        CollectionManager.getCollectionManager().initializeVector();
        Connections connections = new Connections(args[0]);
        connections.run();

//        IntStream.range(0, LISTENERS_COUNT)
//                .forEach((x) -> listenersPool.execute(new ListenerWorker(args[0])));
    }



}