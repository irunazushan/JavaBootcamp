package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.*;


@Component
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository<User> {
    private final NamedParameterJdbcTemplate template;
    @Autowired
    public UsersRepositoryJdbcTemplateImpl(@Qualifier("driverManagerDataSource") DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }
    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT id, email FROM users WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        User user = template.queryForObject(query, namedParameters, (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("email"), rs.getString("password")));
        if (user != null) {
            return Optional.of(user);
        }
        throw new NullPointerException("User with id: '" + id + "' not found");
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT id, email, password FROM users";
        List<User> users;
        users = template.query(query, (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("email"), rs.getString("password")));
        return users;
    }

    @Override
    public void save(User entity) {
        String query = "INSERT INTO users (email, password) VALUES (:email, :password) RETURNING id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email", entity.getEmail())
                .addValue("password", entity.getPassword());
        Long newId = template.queryForObject(query, namedParameters, Long.class);
        entity.setId(newId);
    }

    @Override
    public void update(User entity) {
        String query = "UPDATE users SET email=:email, password=:password WHERE id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email", entity.getEmail())
                .addValue("password", entity.getPassword())
                .addValue("id", entity.getId());
        template.update(query, namedParameters);
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM users WHERE id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        template.update(query, namedParameters);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String query = "SELECT id, email, password FROM users WHERE email = :email";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email", email);
        User user = template.queryForObject(query, namedParameters, (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("email"), rs.getString("password")));
        System.out.println(user);
        assert user != null;
        return Optional.of(user);
    }
}
