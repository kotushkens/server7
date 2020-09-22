package Classes;

import Exceptions.FieldException;

import java.io.Serializable;

public class Chapter implements Serializable {
    private static final long serialVersionUID = 32L;

    private int id;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;
    private String world; //Поле может быть null
    private User user;

    public Chapter (String name, String parentLegion, String world) {
        this.name = name;
        this.parentLegion = parentLegion;
        this.world = world;
    }

    public Chapter() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        if (name == null || name.equals("")) throw new FieldException();
        else return name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWorld() {
        return world;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentLegion() {
        return parentLegion;
    }

    public void setParentLegion(String parentLegion) {
        this.parentLegion = parentLegion;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "имя: " + name + '\'' +
                ", parentLegion: " + parentLegion + '\'' +
                ", world: " + world + '\'' +
                '}';
    }
}

