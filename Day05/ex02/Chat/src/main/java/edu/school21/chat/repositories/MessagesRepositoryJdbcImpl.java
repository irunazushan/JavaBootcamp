package edu.school21.chat.repositories;

import edu.school21.chat.exception.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            throw new NotSavedSubEntityException();
        }
        return Optional.empty();
    }

    public void save(Message message) {
        String query = "INSERT INTO messages (author, room, message, date_time) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            User author = findUserById(message.getAuthor().getId());
            Chatroom room = findChatroomById(message.getRoom().getId());
            ps.setLong(1, author.getId());
            ps.setLong(2, room.getId());
            ps.setString(3, message.getText());
            ps.setTimestamp(4, Timestamp.valueOf(message.getDataTime()));
            if (ps.executeUpdate() > 0) {
                System.out.println("New data inserted to db successfully");
            }
            message.setId(getLastMessageId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private long getLastMessageId() {
        String query = "SELECT id from messages ORDER BY id DESC LIMIT 1";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    public User findUserById(Long id) throws NotSavedSubEntityException {
        User user = null;
        String query = "SELECT * FROM users WHERE id = " + id;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                user = new User(id, login, password, new ArrayList<>(), new ArrayList<>());
            }
        } catch (SQLException e) {
            throw new NotSavedSubEntityException();
        }
        return user;
    }

    public Chatroom findChatroomById(Long id) throws NotSavedSubEntityException {
        Chatroom chatroom = null;
        String query = "SELECT * FROM chatrooms WHERE id = " + id;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                User owner = findUserById(resultSet.getLong("owner"));
                chatroom = new Chatroom(id, name, owner, new ArrayList<>());
            }
        } catch (SQLException e) {
            throw new NotSavedSubEntityException();
        }
        return chatroom;
    }
}
