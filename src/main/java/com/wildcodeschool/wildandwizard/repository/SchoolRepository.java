package com.wildcodeschool.wildandwizard.repository;

import com.wildcodeschool.wildandwizard.entity.School;

import java.sql.*;

public class SchoolRepository {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/spring_jdbc_quest?serverTimezone=GMT";
    private final static String DB_USER = "h4rryp0tt3r";
    private final static String DB_PASSWORD = "Horcrux4life!";

    public School save(String name, Long capacity, String country) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO school (name, capacity, country) VALUES (?, ?, ?)";
            String[] generatedColumns = {"id"};

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, generatedColumns)) {
                preparedStatement.setString(1, name);
                preparedStatement.setLong(2, capacity);
                preparedStatement.setString(3, country);
                preparedStatement.executeUpdate();

                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        Long id = resultSet.getLong(1);
                        return new School(id, name, capacity, country);

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
