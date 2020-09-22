package com.company;


import Classes.SpaceMarine;
import Classes.User;
import com.company.db.DatabaseReader;
import com.company.db.DatabaseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Здесь указаны команды, которые связаны с коллекцией
 */

public class CollectionManager {

    private Logger log = LoggerFactory.getLogger(CollectionManager.class);

    private static List<SpaceMarine> collection;
    private static Date creationDate;
    private static File file;
    private static CollectionManager collectionManager;

    private CollectionManager() {
    }

    public static CollectionManager getCollectionManager() {
        if (collectionManager == null) {
            collectionManager = new CollectionManager();
        }
        return collectionManager;
    }

    public void initializeVector() {
        if (collection == null) {
            collection = new CopyOnWriteArrayList<>();
            creationDate = new Date();
            loadFromDb();
        }
    }

    private void loadFromDb() {
        try (DatabaseReader con = new DatabaseReader()) {
            collection.addAll(con.getAllSpaceMarines());
        } catch (SQLException e) {
            log.error("Не получилось инициализировать коллекцию из базы данных.", e);
        }
    }

    static List<SpaceMarine> getCollection() {
        return collection;
    }

    public void add(SpaceMarine spaceMarine, User user) {
        try (DatabaseWriter con = new DatabaseWriter()) {
            spaceMarine.setUser(user);
            con.saveSpaceMarine(spaceMarine);
            collection.add(spaceMarine);
        } catch (SQLException e) {
            log.error("Не получилось добавить marine", e);
        }
    }

    public static void addFromJson(SpaceMarine spaceMarine) {
        spaceMarine.setID(spaceMarine.getId());
        collection.add(spaceMarine);
    }

    public String getInformation() {
        StringBuilder sb = new StringBuilder();
        sb.append("Тип - ").append(collection.getClass()).append("\n");
        sb.append("Дата - ").append(creationDate).append("\n");
        sb.append("Количество элементов - ").append(collection.size()).append("\n");
        return sb.toString();
    }

    public void clear() {
        try (DatabaseWriter con = new DatabaseWriter()) {
            con.drop();
            collection.clear();
        } catch (SQLException e) {
            log.error("Не получилось очистить хранилище", e);
        }
    }

    public String show() {
        collection.sort(Comparator.comparing(SpaceMarine::getCreationDate)
                .thenComparing(SpaceMarine::getId)
                .thenComparing(SpaceMarine::getHealth)
                .thenComparing(SpaceMarine::getHeartCount));

        String info = collection
                .stream()
                .map(Demonstrate::demonstrate)
                .collect(Collectors.joining(", "));
        if (info.equals("")) {
            info = "Не густо, а пусто";
        }
        return info;

    }

    /*
    public static void show() {
       if (!getCollection().isEmpty()) {
           int i = 1;
           for (SpaceMarine spaceMarine : collection) {
               System.out.println("Элемент под номером " + i);
               System.out.println(spaceMarine.toString());
               i++;
           }
       } else {
           System.out.println("Не густо, а пусто!");
       }
    }

 */


    public void remove_by_id(Integer marineID) {
        SpaceMarine sp = null;
        try (DatabaseWriter con = new DatabaseWriter()) {
            con.deleteMarineById(marineID);
            for (SpaceMarine s : collection) {
                if (s.getId().equals(marineID)) {
                    sp = s;
                    break;
                }
            }
            collection.remove(sp);
        } catch (SQLException e) {
            log.error("Не получилось удалить marine по id", e);
        }
    }


    public void update(SpaceMarine spaceMarine, Integer marineID) {
        try (DatabaseWriter con = new DatabaseWriter()){
            con.updateMarineById(spaceMarine, marineID);
            for (SpaceMarine SpaceMarineUpdate : collection) {
                if (spaceMarine.getId().equals(marineID)) {
                    spaceMarine.setName(SpaceMarineUpdate.getNames());
                    spaceMarine.setID(SpaceMarineUpdate.getId());
                    spaceMarine.setChapter(SpaceMarineUpdate.getChapter());
                    spaceMarine.setMeleeWeapon(SpaceMarineUpdate.getMeleeWeapon());
                    spaceMarine.setHeartCount(SpaceMarineUpdate.getHeartCount());
                    spaceMarine.setHealth(SpaceMarineUpdate.getHealth());
                    spaceMarine.setCoordinates(SpaceMarineUpdate.getCoordinates());
                }
            }
        } catch (SQLException e) {
            log.error("Не получилось обновить marine", e);
        }
    }

    public String ascendingHeight() {
        if (collection.size() > 0) {
            collection.sort(new HeightComparator());
        } else {
            return "Коллекция пуста";
        }
        return ascendingHeight();
    }

    public String descendingHeight() {
        if (collection.size() > 0) {
            collection.sort(new HeightComparator());
            System.out.println("Отсортированный вариант:");
            Collections.reverse(collection);
        } else {
            return "Коллекция пуста";
        }

        return descendingHeight();
    }

    public static void removeL() {
        if (!getCollection().isEmpty()) {
            SpaceMarine marine = collection.get(collection.size() - 1);
            try (DatabaseWriter con = new DatabaseWriter()) {
                con.deleteMarineById(marine.getId());
                collection.remove(collection.get(collection.size() - 1));
                System.out.println("Успешно устранено");
            } catch (SQLException e) {

            }
        } else {
            System.out.println("Не удалось удалить последний элемент потому что нечего удалять");
        }
    }


}

