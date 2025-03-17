package com.example.messaging.manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static final UserManager instance = new UserManager();
    private Connection connection;

    private UserManager() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/messaging_db", "user", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserManager getInstance() {
        return instance;
    }

    public boolean userExists(String username) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT 1 FROM users WHERE username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUser(String username, String password) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMessage(String username, String message) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO messages (username, message) VALUES (?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getMessages(String username, String password) {
        List<String> messages = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT message FROM messages WHERE username = ? AND EXISTS (SELECT 1 FROM users WHERE username = ? AND password = ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(rs.getString("message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
