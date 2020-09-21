package com.company.db;

import com.company.classes.Chapter;
import com.company.classes.Coordinates;
import com.company.classes.SpaceMarine;
import com.company.classes.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseWriter extends DatabaseConnection{

    private Logger log = LoggerFactory.getLogger(DatabaseWriter.class);

    public int saveSpaceMarine(SpaceMarine marine) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO space_marine VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)", GENERATED_COLUMNS)) {
            statement.setString(1, marine.getName());
            statement.setInt(2, saveCoordinates(marine.getCoordinates()));
            statement.setString(3, marine.getCreationDate().toString());
            statement.setLong(4, marine.getHealth());
            statement.setInt(5, marine.getHeartCount());
            statement.setInt(6, marine.getHeight());
            statement.setString(7, marine.getMeleeWeapon().toString().toUpperCase());
            statement.setInt(8, saveChapter(marine.getChapter()));
            statement.setInt(9, marine.getUser().getId());
            if (statement.executeUpdate() != 0) {
                log.debug("Marine added to db!");
            } else {
                log.error("Marine doesn`t added to db.");
            }
            return getInsertId(statement);
        }
    }

    public int saveChapter(Chapter chapter) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO chapter VALUES (?, DEFAULT, ?, ?, ?)", GENERATED_COLUMNS)) {
            statement.setString(1, chapter.getName());
            statement.setString(2, chapter.getParentLegion());
            statement.setInt(3, chapter.getUser().getId());
            statement.setString(4, chapter.getWorld());
//            statement.setString(4, house.ge);
            if (statement.executeUpdate() != 0) {
                log.debug("Chapter added to db!");
            } else {
                throw new SQLException("Chapter doesn`t added to db.");
            }
            return getInsertId(statement);
        }
    }

    public int saveCoordinates(Coordinates coordinates) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO coordinates VALUES (?, default, ?)", GENERATED_COLUMNS)){
            statement.setDouble(1, coordinates.getX());
            statement.setLong(2, coordinates.getY());
            if (statement.executeUpdate() != 0) {
                log.debug("Coordinates saved to db!");
            } else {
                throw new SQLException("Coordinates doesn`t added to db.");
            }
            return getInsertId(statement);
        }
    }

    public int saveUser(User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO \"user\" VALUES (default, ?, ?)", GENERATED_COLUMNS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            if (statement.executeUpdate() != 0) {
                log.debug("User saved to db!");
            } else {
                throw new SQLException("User doesn`t added to db.");
            }
            return getInsertId(statement);
        }
    }

    public void deleteMarineById(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM space_marine WHERE id=?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
            log.debug("Marine with id {} deleted successfully", id);
        }
    }

    public void updateMarineById(SpaceMarine marine, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE space_marine SET name=?, health=?, heart_count=?, height=?, melee_weapon=? WHERE id=?")) {
            statement.setString(1, marine.getName());
            statement.setLong(2, marine.getHealth());
            statement.setInt(3, marine.getHeartCount());
            statement.setInt(4, marine.getHeight());
            statement.setString(5, marine.getMeleeWeapon().toString().toUpperCase());
            statement.setInt(6, id);
            if (statement.execute()) {
                log.info("Successfully updated marine with id {}", id);
            } else {
                log.error("Marine with id {} doesn`t updated", id);
                throw new SQLException("Marine doesn`t updated");
            }
        }
    }

}
