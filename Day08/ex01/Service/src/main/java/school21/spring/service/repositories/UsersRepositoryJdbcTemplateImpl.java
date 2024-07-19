package school21.spring.service.repositories;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.*;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository<User> {
    private final NamedParameterJdbcTemplate template;
    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }
    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT id, email FROM users WHERE id = :id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        User user = template.queryForObject(query, map, (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("email")));
        if (user != null) {
            return Optional.of(user);
        }
        throw new NullPointerException("User with id: '" + id + "' not found");
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT id, email FROM users";
        List<User> users;
        users = template.query(query, (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("email")));
        return users;
    }

    @Override
    public void save(User entity) {
        String query = "INSERT INTO users (email) VALUES (:email) RETURNING id";
        Map<String, Object> map = new HashMap<>();
        map.put("email", entity.getEmail());
        Long newId = template.queryForObject(query, map, Long.class);
        entity.setId(newId);
    }

    @Override
    public void update(User entity) {
        String query = "UPDATE users SET email=:email WHERE id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("email", entity.getEmail());
        map.put("id", entity.getId());
        template.update(query, map);
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM users WHERE id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        template.update(query, map);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String query = "SELECT id, email FROM users WHERE email = :email";
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        User user = template.queryForObject(query, map, (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("email")));
        System.out.println(user);
        assert user != null;
        return Optional.of(user);
    }
}
