package com.company;

import Classes.SpaceMarine;

import java.util.Comparator;

public class HeightComparator implements Comparator<SpaceMarine> {
    public int compare(SpaceMarine height1, SpaceMarine height2) {
        return Integer.compare(height1.getHeight(), height2.getHeight());
    }
}