package edu.school21.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.models.User;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        HikariDataSource dataSource;
        HikariConfig config = new HikariConfig("src/main/resources/database.properties");
        try {dataSource = new HikariDataSource(config);
            OrmManager om = new OrmManager(dataSource);
            User user = new User(null, "Il", "Shan", 23);
            User user2 = new User(null, "Convict", "Webber", 22);
            om.initialize();
            om.save(user);
            om.save(user2);
            user2.setLastName("Irunazu");
            user2.setAge(25);
            System.out.println(om.findById(2L, User.class));
            om.update(user2);
            System.out.println(om.findById(2L, User.class));

        } catch (IllegalArgumentException | InvocationTargetException | SQLException | IllegalAccessException |
                 NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
