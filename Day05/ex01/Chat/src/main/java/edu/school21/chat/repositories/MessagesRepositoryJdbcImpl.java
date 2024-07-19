package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Message> findById(Long id) {
        String query = "SELECT id, author, room, message, date_time FROM messages WHERE id = " + id;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                User user = findUserById(resultSet.getLong("author"));
                Chatroom room = findChatroomById(resultSet.getLong("room"));
                String text = resultSet.getString("message");
                LocalDateTime dataTime = resultSet.getTimestamp("date_time").toLocalDateTime();
                Message message = new Message(resultSet.getLong("id"), user, room, text, dataTime);
                return Optional.of(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private User findUserById(Long id) {
        User user = null;
        String query = "SELECT * FROM users WHERE id = " + id;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                user = new User(id, login, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private Chatroom findChatroomById(Long id) {
        Chatroom chatroom = null;
        String query = "SELECT * FROM chatrooms WHERE id = " + id;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                User owner = findUserById(resultSet.getLong("owner"));
                chatroom = new Chatroom(id, name, owner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatroom;
    }
}
