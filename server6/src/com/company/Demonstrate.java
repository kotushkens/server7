package com.company;

import com.company.classes.SpaceMarine;

import java.util.List;
import java.util.Vector;


public class Demonstrate {

    public static boolean checkExist(int ID) {
        List<SpaceMarine> c = CollectionManager.getCollection();
        for (SpaceMarine spaceMarine:CollectionManager.getCollection()) {
            return spaceMarine.getId().equals(ID);
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
                        "_________________________________________________________\n", collection.getId(), collection.getNames(),
        collection.getCoordinates().toString(), collection.getCreationDate(), collection.getHealth(),collection.getHeartCount(),
        collection.getMeleeWeapon(),collection.getChapter().toString()
        );

        return info;

    }
}
