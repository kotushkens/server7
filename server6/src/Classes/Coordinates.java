package Classes;

import Exceptions.FieldException;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private long x; //Значение поля должно быть больше -538
    private int y;

    public Coordinates(long x, int y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        if (x<-538) throw new FieldException();
        else return x;
    }

    public int getY() {
        return y;
    }
}
