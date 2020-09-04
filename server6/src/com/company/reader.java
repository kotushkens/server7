package com.company;

import Classes.SpaceMarine;
import Exceptions.FileException;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;


public class reader {

    /**
     * Все еще думаю о валидности такого названия класса, но reader потому что мы читаем данные для коллекции в заданном формате, и, соответственно обрабатываем возможные ошибки в синтаксисе этого формата
     * @param file считываем отсюда данные для коллекции
     * @return возвращаем коллекцию
     */
    public static Vector<SpaceMarine> read(File file) {
        Vector<SpaceMarine> collection = new Vector<SpaceMarine>();
        try {
            Scanner scanner = new Scanner(file);
            String words;
            while (scanner.hasNextLine()) {
                words = scanner.nextLine();
                if(words.equals("[") || words.equals("]")) {
                }
                else if (words.charAt(words.length()-1) == ',') {
                    words = words.substring(0, words.length() - 1);
                }

            } return collection;

        } catch (IOException e) {
            throw new FileException("Упс",e);
        } catch (JsonSyntaxException e) {
            throw new FileException("Исправьте синтаксические ошибки",e);
        }
    }
}
