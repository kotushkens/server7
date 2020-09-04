package com.company;

import Classes.SpaceMarine;

public class Check {



    public static boolean SpaceMarineCheck (SpaceMarine spaceMarine) {
        return spaceMarine.getNames()!=null && spaceMarine.getID()!=null && spaceMarine.getCoordinates().getX()>-538 &&
                spaceMarine.getCreationDate()!= null && spaceMarine.getHealth()>0 && spaceMarine.getHeartCount()!=null &&
                spaceMarine.getHeartCount()>0 && spaceMarine.getHeartCount()<=3 && spaceMarine.getHeight()>0 &&
                spaceMarine.getMeleeWeapon()!=null && spaceMarine.getChapter()!=null;
    }
}
