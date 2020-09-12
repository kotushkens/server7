package com.company;

import Classes.SpaceMarine;

import java.util.Vector;


public class Demonstrate {

    public static boolean checkExist(int ID) {
        Vector<SpaceMarine> c = CollectionManager.getCollection();
        for (SpaceMarine spaceMarine:CollectionManager.getCollection()) {
            return spaceMarine.getID().equals(ID);
        }
        return false;
    }

    static String demonstrate(SpaceMarine collection) {

        String info = "";
        info = String.format(
                "Уникальный ID: %s\n" +
                "Имя: %s\n" +
                "Координаты: %s\n" +
                "Дата создания: %s\n" +
                "Здоровье: %s\n" +
                "Heart Count: %s\n"+
                "Melee Weapon: %s\n" +
                "Chapter: %s\n" +
                        "_________________________________________________________\n", collection.getID(), collection.getNames(),
        collection.getCoordinates().toString(), collection.getCreationDate(), collection.getHealth(),collection.getHeartCount(),
        collection.getMeleeWeapon(),collection.getChapter().toString()
        );

        return info;

    }
}
