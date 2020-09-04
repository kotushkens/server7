package com.company;



import Classes.SpaceMarine;
import Exceptions.FileReaderException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Здесь указаны команды, которые связаны с коллекцией
 */

public class CollectionManager {
    private static Vector<SpaceMarine> collection;
    private static Date creationDate;
    private static File file;
    private static CollectionManager collectionManager;

    private CollectionManager() {}

    public static CollectionManager getCollectionManager() {
        if (collectionManager == null){
            collectionManager = new CollectionManager();
        }
        return collectionManager;
    }

    public static void initializeVector() {
        if (collection == null) { collection = new Vector<SpaceMarine>(); creationDate = new Date(); }
    }

    static Vector<SpaceMarine> getCollection() {
        return collection;
    }

    public static void add(SpaceMarine spaceMarine) {
        collection.add(spaceMarine);
    }

    public static void addFromJson(SpaceMarine spaceMarine) {
        spaceMarine.setID(spaceMarine.getID());
        collection.add(spaceMarine);
    }

    public String getInformation() {
        String info ="";
        System.out.println("Тип - "+ collection.getClass());
        System.out.println("Дата - "+ creationDate);
        System.out.println("Количество элементов - "+ collection.size());
        return info;
    }

    public static void clear() {
        collection.clear();
    }

    public String show() {
        collection.sort(Comparator.comparing(SpaceMarine::getCreationDate).thenComparing(SpaceMarine::getID).thenComparing(SpaceMarine::getHealth).thenComparing(SpaceMarine::getHeartCount));

        String info = collection
                .stream()
                .map(Demonstrate::demonstrate)
                .collect(Collectors.joining(", ")    );
        if (info.equals("")) { info = "Не густо, а пусто"; }
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


    public static void remove_by_id(Integer marineID) {
        SpaceMarine sp=null;
        for(SpaceMarine s:collection){
            if(s.getID().equals(marineID)){
                sp=s;
                break;
            }
        }
        collection.remove(sp);
    }


    public static void update(SpaceMarine spaceMarine, Integer marineID) {
        for (SpaceMarine SpaceMarineUpdate  : collection) {
            if (spaceMarine.getID().equals(marineID)) {
                spaceMarine.setName(SpaceMarineUpdate.getNames());
                spaceMarine.setID(SpaceMarineUpdate.getID());
                spaceMarine.setChapter(SpaceMarineUpdate.getChapter());
                spaceMarine.setMeleeWeapon(SpaceMarineUpdate.getMeleeWeapon());
                spaceMarine.setHeartCount(SpaceMarineUpdate.getHeartCount());
                spaceMarine.setHealth(SpaceMarineUpdate.getHealth());
                spaceMarine.setCoordinates(SpaceMarineUpdate.getCoordinates());
            }
        };
    }

    public String  AscendingHeight() {
        HeightComparator myHeightComparator = new HeightComparator();
        if (collection.size()>0) {
            collection.sort(myHeightComparator);
        }

        else {return "Коллекция пуста"; }
        return AscendingHeight();
    }

    public String DescendingHeight() {
        HeightComparator myHeightComparator = new HeightComparator();
        if (collection.size()>0) {
            collection.sort(myHeightComparator);
            System.out.println("Отсортированный вариант:");
            Collections.reverse(collection);
        }
        else {
            return "Коллекция пуста";
        }

        return DescendingHeight();
    }

    public static void removeL() {
        if (!getCollection().isEmpty()) {
            collection.remove(collection.lastElement());
            System.out.println("Успешно устранено");
        }
        else {
            System.out.println("Не удалось удалить последний элемент потому что нечего удалять");
        }
    }


}

