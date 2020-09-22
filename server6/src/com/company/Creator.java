package com.company;

import Classes.Chapter;
import Classes.Coordinates;
import Classes.MeleeWeapon;
import Classes.SpaceMarine;
import Exceptions.FieldException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import static Classes.MeleeWeapon.MANREAPER;

/**
 * Своего рода фабрика по созданию всего того, что нужно создать
 */

public class Creator {

    public class SpaceMarineCreator {
        SpaceMarineCreator() {
        }
    }

    private static Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private static String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private static long health; //Значение поля должно быть больше 0
    private static Integer heartCount; //Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 3
    private static int height;
    private static MeleeWeapon meleeWeapon; //Поле не может быть null
    private static Chapter chapter; //Поле не может быть null
    private static long x; //Значение поля должно быть больше -538
    private static int y;
    private static String parentLegion;
    private static String world; //Поле может быть null


    public static Integer setID() {
        id = 0;
        id++;
        return id;
    }

    public static Integer getID() {
        return id;
    }
        public static void setName() {
            Scanner names = new Scanner(System.in);
            name = names.nextLine();


            try {
                Double d1 = new Double(name);
                System.out.println("Мы все понимаем, но даже название корабля не может состоять лишь из цифр! Попробуйте еще раз");
                setName();
            } catch (NumberFormatException e) {
                if (name.length() >= 1) {
                    name = name;
                } else {
                    System.out.println("Пустое название? Переосмыслите свои убеждения и придумайте название корабля!");
                    setName();
                }
                ;
            }
        }

        public static void setX() {
        Scanner scanner = new Scanner(System.in);
            String corX = scanner.nextLine();

            try {
                Long d = Long.parseLong(corX);
                if ( d > -538) {
                    x = d;
                } else {
                    System.out.println("Одному Богу известно почему координата не может быть меньше -538, но это так, попробуйте снова");
                    setX();
                }
            } catch (NumberFormatException e) {
                System.out.println("Ну вот опять что-то не так, попробуйте снова");
                setX();
            }
        }

        public static void setY() {
        Scanner scanner = new Scanner(System.in);
            String corY = scanner.nextLine();


            try {
                Integer s = Integer.parseInt(corY);
                y = s;
            } catch (NumberFormatException e) {
                System.out.println("Не знаем, что могло пойти не так, но что-то пошло не так. Попробуйте снова");
                setY();
            }
        }

        public static void setHealth() {
            Scanner scanner = new Scanner(System.in);
            String healthy = scanner.nextLine();

            try {
                Long h = Long.parseLong(healthy);
                if ( h > 0) {
                    health = h;
                } else {
                    System.out.println("До кого-то добрался boomer remover? Как тогда здоровье может быть отрицательным?");
                    setHealth();
                }
            } catch (NumberFormatException e) {
                System.out.println("Ну вот опять что-то не так, попробуйте снова");
                setHealth();
            }

        }

        public static void setHeartCount() {
            Scanner scanner = new Scanner(System.in);
            String heartCounts = scanner.nextLine();


            try {
                Integer HC = Integer.parseInt(heartCounts);
                if ((HC > 0) && (HC <= 3)) {
                    heartCount = HC;
                } else {
                    System.out.println("Кажется, кто-то мертв вниутри ну или тахикардия. Попробуй снова");
                    setHeartCount();
                }

            } catch (NumberFormatException e) {
                System.out.println("Не знаем, что могло пойти не так, но что-то пошло не так. Попробуйте снова");
                setHeartCount();
            }
        }

        public static void setHeight() {
            Scanner scanner = new Scanner(System.in);
            String heights = scanner.nextLine();


            try {
                Integer hei = Integer.parseInt(heights);
                if (hei>0) {
                    height = hei;
                } else {
                    System.out.println("Отрицательный рост это что-то новое, попробуйте еще раз");
                    setHeight();
                }
            } catch (NumberFormatException e) {
                System.out.println("Не знаем, что могло пойти не так, но что-то пошло не так. Попробуйте снова");
                setHeight();
            }
        }

        public static void setChapter() {
            System.out.println("Вы уже ввели имя " + name);
            System.out.println("Значение parentLegion");
            Scanner parentLegions = new Scanner(System.in);
            parentLegion = parentLegions.nextLine();

            System.out.println("Значение world");
            Scanner worlds = new Scanner(System.in);
            world = worlds.nextLine();

            try {
                Double l1 = new Double(parentLegion);
                Double w1 = new Double(world);
                System.out.println("Будь как лекции по матеше и не используй только цифры");
                setChapter();
            } catch (NumberFormatException e) {
                if ((world.length()>=1) && ((parentLegion.length()>=1))) {
                    world = world;
                    parentLegion = parentLegion;
                } else {
                    System.out.println("Мне лень придумывать уже, просто что-то не так");
                    setChapter();
                }
            }
        }
        public Coordinates getCoordinates() {
        if (coordinates == null) throw new FieldException();
        else return coordinates;
    }
        public static void setMeleeWeapon() {

            System.out.println("Введите одно из значений, введите номер выбранного значения:");
            System.out.println("1 – POWER_SWORD");
            System.out.println("2 – CHAIN_AXE");
            System.out.println("3 – MANREAPER");
            System.out.println("4 — LIGHTING_CLAW");
            Scanner scanner = new Scanner(System.in);
            String meleeWeaponVariants = scanner.nextLine();


            if (meleeWeaponVariants.equals("1") || meleeWeaponVariants.equals("2") || meleeWeaponVariants.equals("3") || meleeWeaponVariants.equals("4")) {
                switch (meleeWeaponVariants) {
                    case ("1"):
                        meleeWeapon = MeleeWeapon.POWER_SWORD;

                    case ("2"):
                        meleeWeapon = MeleeWeapon.CHAIN_AXE;

                    case ("3"):
                        meleeWeapon = MANREAPER;
                    case ("4"):
                        meleeWeapon = MeleeWeapon.LIGHTING_CLAW;
                }
                System.out.println("Элемент выбран!");
            } else {
                System.out.println("Что-то не так, попробуйте еще раз");
                setMeleeWeapon();
            }
        }

        /**
         *
         * @return new создаем SpaceMarine
         */

        public static SpaceMarine SpaceMarineCreator() {
            System.out.println("Значение уникального id " + setID());
            System.out.println("Введите название:");
            setName();
            System.out.println("Введите значение координаты X:");
            setX();
            System.out.println("Введите значение координаты Y:");
            setY();
            System.out.println("Введите значение поля health:");
            setHealth();
            System.out.println("Введите значение поля heartCount:");
            setHeartCount();
            System.out.println("Введите значение поля height:");
            setHeight();
            System.out.println("Убедитесь, что chapter существует:");
            setChapter();
            System.out.println("Еще какое-то поле:");
            setMeleeWeapon();

            SpaceMarine sp = new SpaceMarine(id,name,new Coordinates(x,y), new Date(),health,heartCount,height,meleeWeapon,chapter);
            return sp;
        }

        public static SpaceMarine ScriptFromJsonToCollection(ArrayList<String> field) {
            if (data(field)) {
                return new SpaceMarine(Integer.parseInt(field.get(0)), name.valueOf(field.get(1)), new Coordinates(Integer.parseInt(field.get(2)), Integer.parseInt(field.get(3))), new Date(field.get(4)), Long.parseLong(field.get(5)), Integer.parseInt(field.get(6)), Integer.parseInt(field.get(7)),MeleeWeapon.valueOf(field.get(8)),new Chapter(field.get(9),field.get(10),field.get(11)));
            } else {
                System.out.println("Ошибка в параметрах");
            }
            return  null;
        }

        private static boolean data(ArrayList<String> field) {
            try {
                return (!field.get(0).isEmpty()) && (field.get(1).isEmpty()) && (Integer.parseInt(field.get(2)) > -538) && (Integer.parseInt(field.get(5)) > 0) && (Integer.parseInt(field.get(6)) > 0) && ((Integer.parseInt(field.get(7))) > 0) && (Integer.parseInt(field.get(6)) <3);

            } catch (NumberFormatException e) {
                return false;
            }

        }
}



