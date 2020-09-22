package com.company;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

import Classes.User;
import com.company.db.DatabaseReader;
import com.company.db.DatabaseWriter;
import com.company.server.workers.SenderWorker;
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

    private User checkAuth(User user) {
        if (user == null) {
            sendMessage("Не верный логин и/или пароль. Ну или ты вообще login не прописывал...");
            return null;
        }
        try (DatabaseReader con = new DatabaseReader()) {
            User user1 = con.getUserByLogin(user.getLogin()).orElse(null);
            if (user1 == null) {
                sendMessage("Таких не знаем, иди регайся...");
                return null;
            }
            user1.checkHash();
            user.checkHash();
            if (user1.getPassword().equals(user.getPassword())) {
                return user1;
            } else {
                sendMessage("Дружок, а пароль то не тот!");
                return null;
            }
        } catch (SQLException e) {
            logger.error("Не получилось проверить авторизацию");
            sendMessage("Команда доступна только авторизированным юзерам, а ты ещё смешарик.");
            return null;
        }
    }

    public void info(User user) {
        if (checkAuth(user) == null) {
            return;
        }
        sendMessage(collectionManager.getInformation());
        logger.info(String.format("Клиенту %s:%s отправлена информация", socket.getInetAddress(), socket.getPort()));
    }


    public void show(User user) {
        if (checkAuth(user) == null) {
            return;
        }
        sendMessage(collectionManager.show());
        logger.info(String.format("Клиенту %s:%s отправлен результат работы «показать»", socket.getInetAddress(), socket.getPort()));
    }


    public void add(Object o, User user) {
        User userFromDb = checkAuth(user);
        if (userFromDb == null) {
            return;
        }
        user = userFromDb;
        SpaceMarine spaceMarine = (SpaceMarine) o;
        if (Check.SpaceMarineCheck(spaceMarine)) {
            collectionManager.add(spaceMarine, user);
            sendMessage("Успешно добавлено");
        } else {
            sendMessage("Ошибочка! Проверьте введенные данные");
        }

        logger.info(String.format("Клиенту %s:%s отправлен результат работы «добавить»", socket.getInetAddress(), socket.getPort()));

    }

    public void clear(User user){
        if (checkAuth(user) == null) {
            return;
        }
        collectionManager.clear();
        sendMessage("Коллекция успешно очищена");
        logger.info(String.format("Клиенту %s:%s отправлен результат очистки", socket.getInetAddress(), socket.getPort()));
    }

    public void exit() {
        logger.info("Всё, пока!");
        System.exit(0);
    }


    public void ascendingHeight(User user) {
        if (checkAuth(user) == null) {
            return;
        }
        sendMessage(collectionManager.ascendingHeight());
        logger.info(String.format("Клиенту %s:%s отправлен резальтат сортировки", socket.getInetAddress(), socket.getPort()));
    }

    public void descendingHeight(User user) {
        if (checkAuth(user) == null) {
            return;
        }
        sendMessage(collectionManager.descendingHeight());
        logger.info(String.format("Клиенту %s:%s отправлен резальтат сортировки", socket.getInetAddress(), socket.getPort()));
    }



    public void remove_by_id(String ID, User user) {
        int marineID;
        User userFromDb = checkAuth(user);
        if (userFromDb == null) {
            return;
        }

        try {
            marineID = Integer.parseInt(ID);
            if (Demonstrate.checkExist(marineID)) {
                SpaceMarine marine = getMarineById(marineID).orElseThrow();
                if (marine.getUser().getId() != user.getId()) {
                    sendMessage("Это не твой объект. Руки прочь!");
                    return;
                }
                collectionManager.remove_by_id(marineID);
                sendMessage("Элемент с ID " + marineID + " успешно удален из коллекции.");
            } else {
                sendMessage("Это кто ху?");
            }
        } catch (NumberFormatException e) {
            sendMessage("bruh!");
        }
    }

    public Optional<SpaceMarine> getMarineById(int id) {
        try (DatabaseReader con = new DatabaseReader()) {
            return con.getSpaceMarineById(id);
        } catch (SQLException e) {
            logger.error("Не удалось получить марина по id");
            return Optional.empty();
        }
    }


    public void removeLast(User user) {
        if (checkAuth(user) == null) {
            return;
        }
        CollectionManager.removeL();
        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды remove last", socket.getInetAddress(), socket.getPort()));
    }

    /**
     *
     * @param ID - апдейт элемента по ID.
     */
    public void update(String ID, SpaceMarine spaceMarine, User user) {
        int groupId;
        try {
            groupId = Integer.parseInt(ID);
            if (Demonstrate.checkExist(groupId)) {
                SpaceMarine marine = getMarineById(groupId).orElseThrow();
                if (marine.getUser().getId() != user.getId()) {
                    sendMessage("Это не твой объект. Руки прочь!");
                }
                if (Check.SpaceMarineCheck(spaceMarine)) {
                    collectionManager.update(spaceMarine, groupId);
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

    public void register(User user) {
        user.checkHash(); // на всякий пожарный
        try (DatabaseReader reader = new DatabaseReader()) {
            if (reader.getUserByLogin(user.getLogin()).isPresent()) {
                sendMessage("Пользователь с логином " + user.getLogin() + " уже существует.");
                return;
            }
            try (DatabaseWriter writer = new DatabaseWriter()){
                writer.saveUser(user);
                sendMessage("Вы успешно зарегались в нашей системе!");
            }
        } catch (SQLException e) {
            logger.error("Не получилось зарегать пользователя", e);
            sendMessage("Регистрация прервана неизвестной ошибкой (в ПИПе мы такое называем 500)");
        }
        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды REGISTER", socket.getInetAddress(), socket.getPort()));

    }


}

