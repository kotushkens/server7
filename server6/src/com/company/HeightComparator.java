package com.company;

import Classes.SpaceMarine;

import java.util.Comparator;

public class HeightComparator implements Comparator<SpaceMarine> {
    public int compare(SpaceMarine height1, SpaceMarine height2) {
        if (height1.getHeight() == height2.getHeight()) {
            return 0;
        }

        if (height1.getHeight() > height2.getHeight()) {
            return 1;
        }

        else {
            return -1;
        }
    }
}