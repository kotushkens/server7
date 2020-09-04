package Classes;

import Exceptions.FieldException;

import java.io.Serializable;
import java.util.Date;

public class SpaceMarine implements Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long health; //Значение поля должно быть больше 0
    private Integer heartCount; //Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 3
    private int height;
    private MeleeWeapon meleeWeapon; //Поле не может быть null
    private Chapter chapter; //Поле не может быть null

    public SpaceMarine(Integer id, String name, Coordinates coordinates, Date creationDate, long health, Integer heartCount, int height, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.health = health;
        this.heartCount = heartCount;
        this.height = height;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public Integer getID() {
            return id;
    }

    public Coordinates getCoordinates() {
        if (!(coordinates == null)) {
            return coordinates;
        } else throw new FieldException();
    }

    public Date getCreationDate() {
        if (!(creationDate == null)) {
            return creationDate;
        } else throw new FieldException();
    }

    public long getHealth() {
        if (!(health <= 0)){
            return health;
        } else throw new FieldException();
    }

    public int getHeight() {
        return height;
    }

    public Integer getHeartCount() {
        if (!(heartCount == null) || (heartCount>0 && heartCount<3)) {
            return heartCount;
        } else throw new FieldException();
    }

    public MeleeWeapon getMeleeWeapon() {
        if (!(meleeWeapon == null)) {
            return meleeWeapon;
        }

        else throw new FieldException();
    }

    public Chapter getChapter() {
        if (!(chapter == null)) {
            return chapter;
        } else throw new FieldException();
    }

    public String getNames() {
        if (!(name == null)|| !(name.equals(""))) {
            return name;
        }
        else throw new FieldException();
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHeartCount(Integer heartCount) {
        this.heartCount = heartCount;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setHealth(long health){
        this.health = health;
    }
}


