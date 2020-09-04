package Classes;

import Exceptions.FieldException;

import java.io.Serializable;

public class Chapter implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;
    private String world; //Поле может быть null

    public Chapter (String name, String parentLegion, String world) {
        this.name = name;
        this.parentLegion = parentLegion;
        this.world = world;
    }

    public String getName() {
        if (name == null || name.equals("")) throw new FieldException();
        else return name;
    }

    public String getWorld() {
        return world;
    }
}
