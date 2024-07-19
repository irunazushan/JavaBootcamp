package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@Component
public class MessagesRepositoryImpl implements MessagesRepository<Message> {
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public MessagesRepositoryImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<Message> findById(Long id) {
        String query = "SELECT id, sender, chatroom, message, sent_time FROM messages WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        Message message = template.queryForObject(query, namedParameters, (rs, rowNum) ->
                new Message(rs.getLong("id"),
                        findUserById(rs.getLong("sender")),
                        findChatroomById(rs.getLong("chatroom")),
                        rs.getString("message"),
                        rs.getTimestamp("sent_time") != null ? rs.getTimestamp("sent_time").toLocalDateTime() : null));
        if (message != null) {
            return Optional.of(message);
        }
        throw new NullPointerException("Message with id: '" + id + "' not found");
    }

    @Override
    public List<Message> findAll() {
        String query = "SELECT * FROM messages";
        List<Message> messages;
        messages = template.query(query, (rs, rowNum) -> new Message(
                rs.getLong("id"),
                findUserById(rs.getLong("sender")),
                findChatroomById(rs.getLong("chatroom")),
                rs.getString("message"),
                rs.getTimestamp("sent_time") != null ? rs.getTimestamp("sent_time").toLocalDateTime() : null));
        return messages;
    }

    @Override
    public void save(Message entity) {
        String query = "INSERT INTO messages (sender, chatroom, message) VALUES (:sender, :chatroom, :message) RETURNING id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("sender", entity.getSender().getId())
                .addValue("chatroom", entity.getChatroom().getId())
                .addValue("message", entity.getMessage());
        Long newId = template.queryForObject(query, namedParameters, Long.class);
        entity.setId(newId);
    }

    @Override
    public void update(Message entity) {
        String query = "UPDATE messages SET sender=:sender, chatroom=:chatroom, message=:message, sent_time=:sent_time WHERE id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("sender", entity.getSender().getId())
                .addValue("chatroom", entity.getChatroom().getId())
                .addValue("message", entity.getMessage())
                .addValue("sent_time", entity.getDateTime());
        template.update(query, namedParameters);
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM messages WHERE id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        template.update(query, namedParameters);
    }

    @Override
    public List<Message> getMessagesByUserId(Long id) {
        String query = "SELECT id, sender, chatroom, message, sent_time FROM messages WHERE sender=:sender";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("sender", id);
        List<Message> messages = template.query(query, namedParameters, (rs, rowNum) -> new Message(
                rs.getLong("id"),
                findUserById(rs.getLong("sender")),
                findChatroomById(rs.getLong("chatroom")),
                rs.getString("message"),
                rs.getTimestamp("sent_time") != null ? rs.getTimestamp("sent_time").toLocalDateTime() : null));
        return messages;
    }

    public User findUserById(Long id) {
        String query = "SELECT id, name, password FROM users WHERE id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        User user = template.queryForObject(query, namedParameters, (rs, rowNum) -> new User(
                rs.getLong("id"), rs.getString("name"), rs.getString("password"))
        );
        if (user != null) {
            return user;
        }
        throw new NullPointerException("User with id: '" + id + "' not found");
    }

    public Chatroom findChatroomById(Long id) {
        String query = "SELECT id, name, creator FROM chatrooms WHERE id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        Chatroom room = template.queryForObject(query, namedParameters, (rs, rowNum) -> new Chatroom(
                rs.getLong("id"), rs.getString("name"), findUserById(rs.getLong("creator")), null)
        );
        if (room != null) {
            return room;
        }
        throw new NullPointerException("Room with id: '" + id + "' not found");
    }
}