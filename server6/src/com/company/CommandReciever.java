package com.company;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

import com.company.server.workers.SenderWorker;
import org.slf4j.Logger;
import com.company.classes.SpaceMarine;
import com.company.сommands.SerializedMessage;
import org.slf4j.LoggerFactory;

/**
* «Логика» имеющихся команд
 */

public class CommandReciever {
    private final Socket socket;
    private static final Logger logger = LoggerFactory.getLogger(CommandReciever.class);
    private final CollectionManager collectionManager = CollectionManager.getCollectionManager();
    private ExecutorService senderPool = new ForkJoinPool();

    public CommandReciever(Socket socket) {
        this.socket = socket;
    }

    private void sendMessage(String message) {
        try {
            senderPool.execute(new SenderWorker(socket, new SerializedMessage(message)));
        } catch (IOException e) {
            logger.error("Не получилось открыть поток для объектов.", e);
        }
    }

    public void info() {
        sendMessage(collectionManager.getInformation());
        logger.info(String.format("Клиенту %s:%s отправлена информация", socket.getInetAddress(), socket.getPort()));
    }


    public void show() {
        sendMessage(collectionManager.show());
        logger.info(String.format("Клиенту %s:%s отправлен результат работы «показать»", socket.getInetAddress(), socket.getPort()));
    }


    public void add(Object o) {
        SpaceMarine spaceMarine = (SpaceMarine) o;
        if (Check.SpaceMarineCheck(spaceMarine)) {
            CollectionManager.add(spaceMarine);
            sendMessage("Успешно добавлено");
        } else {
            sendMessage("Ошибочка! Проверьте введенные данные");
        }

        logger.info(String.format("Клиенту %s:%s отправлен результат работы «добавить»", socket.getInetAddress(), socket.getPort()));

    }

    public void clear(){
        CollectionManager.clear();
        sendMessage("Коллекция успешно очищена");
        logger.info(String.format("Клиенту %s:%s отправлен результат очистки", socket.getInetAddress(), socket.getPort()));
    }

    public void exit() {
        logger.info("Всё, пока!");
        System.exit(0);
    }


    public void ascendingHeight() {
        sendMessage(collectionManager.ascendingHeight());
        logger.info(String.format("Клиенту %s:%s отправлен резальтат сортировки", socket.getInetAddress(), socket.getPort()));
    }

    public void descendingHeight() {
        sendMessage(collectionManager.descendingHeight());
        logger.info(String.format("Клиенту %s:%s отправлен резальтат сортировки", socket.getInetAddress(), socket.getPort()));
    }



    public void remove_by_id(String ID) {
        int marineID;
        try {
            marineID = Integer.parseInt(ID);
            if (Demonstrate.checkExist(marineID)) {
                CollectionManager.remove_by_id(marineID);
                sendMessage("Элемент с ID " + marineID + " успешно удален из коллекции.");
            } else {
                sendMessage("Это кто ху?");
            }
        } catch (NumberFormatException e) {
            sendMessage("bruh!");
        }
    }


    public void removeLast() {
        CollectionManager.removeL();
        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды remove last", socket.getInetAddress(), socket.getPort()));
    }

    /**
     *
     * @param ID - апдейт элемента по ID.
     */
    public void update(String ID, SpaceMarine spaceMarine) {
        int groupId;
        try {
            groupId = Integer.parseInt(ID);
            if (Demonstrate.checkExist(groupId)) {
                if (Check.SpaceMarineCheck(spaceMarine)) {
                    CollectionManager.update(spaceMarine, groupId);
                    sendMessage("Выполнено");
                } else {
                    sendMessage("Неверные данные элемента");
                }
            }
            else {
                sendMessage("Элемента с таким ID нет в коллекции");
            }
        } catch (NumberFormatException e) {
            sendMessage("Некорректный аргумент.");
        }

        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды UPDATE", socket.getInetAddress(), socket.getPort()));
    }

}

