package school21.spring.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "school21.spring.service")
public class TestApplicationConfig {

    @Bean
    public DataSource embeddedDatabaseBuilder() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScripts("data.sql")
                .build();
        return dataSource;
    }

    @Bean
    public UsersRepository usersRepositoryJdbcImpl() {
        return new UsersRepositoryJdbcImpl(embeddedDatabaseBuilder());
    }
}
