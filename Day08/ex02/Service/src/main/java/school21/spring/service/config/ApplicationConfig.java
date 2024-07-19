package school21.spring.service.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = "school21.spring.service")
public class ApplicationConfig {
    @Value( "${db.driver.name}" )
    private String driverClassName;
    @Value( "${db.url}" )
    private String url;
    @Value( "${db.user}" )
    private String username;
    @Value( "${db.password}" )
    private String password;

    @Bean
    public DataSource driverManagerDataSource() {
        DriverManagerDataSource dm = new DriverManagerDataSource();
        dm.setDriverClassName(driverClassName);
        dm.setUrl(url);
        dm.setUsername(username);
        dm.setPassword(password);
        return dm;
    }

    @Bean
    public DataSource hikariDataSource() {
        HikariDataSource hds = new HikariDataSource();
        hds.setDriverClassName(driverClassName);
        hds.setJdbcUrl(url);
        hds.setUsername(username);
        hds.setPassword(password);
        return hds;
    }
}
