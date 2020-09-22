package com.company.db;

import Classes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DatabaseReader extends DatabaseConnection {

    private final Logger logger = LoggerFactory.getLogger(DatabaseReader.class);

    public Optional<User> getUserByLogin(String login) {
        String sql = "SELECT * FROM \"user\" WHERE \"login\"=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setId(resultSet.getInt("id"));
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("SQLException in getUserByLogin()", e);
            return Optional.empty();
        }
    }

    public Optional<User> getUserById(int id) {
        String sql = "SELECT * FROM \"user\" WHERE \"id\"=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setId(resultSet.getInt("id"));
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("SQLException in getUserById()", e);
            return Optional.empty();
        }
    }

    public Optional<Chapter> getChapterById(int id) {
        String sql = "SELECT * FROM \"chapter\" WHERE \"id\"=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Chapter chapter = new Chapter();
                chapter.setName(resultSet.getString("name"));
                chapter.setParentLegion(resultSet.getString("parent_legion"));
                chapter.setWorld(resultSet.getString("world"));
                chapter.setUser(getUserById(resultSet.getInt("user_id")).orElse(null));
                return Optional.of(chapter);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("SQLException in getChapterById()", e);
            return Optional.empty();
        }
    }

    public Optional<Coordinates> getCoordinatesById(int id) {
        String sql = "SELECT * FROM \"coordinates\" WHERE \"id\"=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Coordinates coordinates = new Coordinates();
                coordinates.setX(resultSet.getLong("x"));
                coordinates.setY(resultSet.getInt("y"));
                coordinates.setUser(getUserById(resultSet.getInt("user_id")).orElse(null));
                return Optional.of(coordinates);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("SQLException in getCoordinatesById()", e);
            return Optional.empty();
        }
    }

    public Optional<SpaceMarine> getSpaceMarineById(int id) {
        String sql = "SELECT * FROM \"space_marine\" WHERE \"id\"=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildSpaceMarine(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("SQLException in getSpaceMarineById()", e);
            return Optional.empty();
        }
    }

    public List<SpaceMarine> getAllSpaceMarines() {
        String sql = "SELECT * FROM \"space_marine\"";
        List<SpaceMarine> marines = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                marines.add(buildSpaceMarine(resultSet));
            }
        } catch (SQLException e) {
            logger.error("SQLException in getAllSpaceMarines()", e);
        }
        return marines;
    }

    private SpaceMarine buildSpaceMarine(ResultSet rs) throws SQLException {
        SpaceMarine res = new SpaceMarine();
        res.setID(rs.getInt("id"));
        res.setChapter(getChapterById(rs.getInt("chapter_id")).orElse(null));
        res.setCoordinates(getCoordinatesById(rs.getInt("coordinates_id")).orElse(null));
        res.setHealth(rs.getLong("health"));
        res.setHeartCount(rs.getInt("heart_count"));
        res.setMeleeWeapon(MeleeWeapon.valueOf(rs.getString("melee_weapon").toUpperCase()));
        res.setName(rs.getString("name"));
        res.setUser(getUserById(rs.getInt("user_id")).orElse(null));
        try {
            res.setCreationDate(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(rs.getString("creation_date")));
        } catch (ParseException e) {
            logger.error("Не удалось распарсить дату создания.", e);
            res.setCreationDate(new Date());
        }
        res.setHeight(rs.getInt("height"));
        return res;
    }
}
