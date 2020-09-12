package com.company;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import Classes.SpaceMarine;
import Commands.SerializedMessage;
import org.slf4j.LoggerFactory;

/**
* «Логика» имеющихся команд
 */

public class CommandReciever {
    private final Socket socket;
    private static final Logger logger = LoggerFactory.getLogger(CommandReciever.class);
    private CollectionManager collectionManager = CollectionManager.getCollectionManager();

    public CommandReciever(Socket socket) {
        this.socket = socket;
    }



    public void info() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        out.writeObject(new SerializedMessage(collectionManager.getInformation()));
        logger.info(String.format("Клиенту %s:%s отправлена информация", socket.getInetAddress(), socket.getPort()));
    }


    public void show() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        out.writeObject(new SerializedMessage(collectionManager.show()));
        logger.info(String.format("Клиенту %s:%s отправлен результат работы «показать»", socket.getInetAddress(), socket.getPort()));
    }


    public void add(Object o) throws IOException {
        //CollectionManager.add(Creator.SpaceMarineCreator());

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        SpaceMarine spaceMarine = (SpaceMarine) o;

        if (Check.SpaceMarineCheck(spaceMarine)) {
            CollectionManager.add(spaceMarine);
            out.writeObject(new SerializedMessage("Успешно добавлено"));
        } else {
            out.writeObject(new SerializedMessage("Ошибочка! Проверьте введенные данные"));
        }

        logger.info(String.format("Клиенту %s:%s отправлен результат работы «добавить»", socket.getInetAddress(), socket.getPort()));

    }

    public void clear() throws IOException {
        CollectionManager.clear();
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        out.writeObject(new SerializedMessage("Коллекция успешно очищена"));
        logger.info(String.format("Клиенту %s:%s отправлен результат очистки", socket.getInetAddress(), socket.getPort()));
    }

    public void exit() {
        System.out.println("все пока!");
        System.exit(0);
    }


    public void ascendingHeight() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(new SerializedMessage(collectionManager.AscendingHeight()));
        logger.info(String.format("Клиенту %s:%s отправлен резальтат сортировки", socket.getInetAddress(), socket.getPort()));
    }

    public void descendingHeight() throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(new SerializedMessage(collectionManager.DescendingHeight()));
        logger.info(String.format("Клиенту %s:%s отправлен резальтат сортировки", socket.getInetAddress(), socket.getPort()));
    }



    public void remove_by_id(String ID) throws IOException {
        Integer marineID;
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        try {
            marineID = Integer.parseInt(ID);
            if (Demonstrate.checkExist(marineID)) {
                CollectionManager.remove_by_id(marineID);
                out.writeObject(new SerializedMessage("Элемент с ID " + marineID + " успешно удален из коллекции."));
            } else out.writeObject(new SerializedMessage("Это кто ху?"));
        } catch (NumberFormatException e) {
            out.writeObject(new SerializedMessage("bruh!"));
        }
    }


    public void removeLast() throws IOException {

        collectionManager.removeL();
        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды remove last", socket.getInetAddress(), socket.getPort()));

    }

    /**
     *
     * @param ID - апдейт элемента по ID.
     */
    public void update(String ID, SpaceMarine spaceMarine) throws IOException {
        Integer groupId;
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        try {
            groupId = Integer.parseInt(ID);
            if (Demonstrate.checkExist(groupId)) {
                if (Check.SpaceMarineCheck(spaceMarine)) {
                    collectionManager.update(spaceMarine, groupId);
                    out.writeObject(new SerializedMessage("Выполнено"));
                } else {
                    out.writeObject(new SerializedMessage("Неверные данные элемента"));
                }
            }
            else {out.writeObject(new SerializedMessage("Элемента с таким ID нет в коллекции"));}
        } catch (NumberFormatException e) {
            out.writeObject(new SerializedMessage("Некорректный аргумент."));
        }

        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды UPDATE", socket.getInetAddress(), socket.getPort()));
    }

}

