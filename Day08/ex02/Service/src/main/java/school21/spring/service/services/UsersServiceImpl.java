package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.UUID;

@Component
public class UsersServiceImpl implements UsersService {
    private UsersRepository ur;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbcImpl") UsersRepository ur) {
        this.ur = ur;
    }

    @Override
    public String signUp(String email) {
        String password = UUID.randomUUID().toString();
        User user = new User (null, email, password);
        ur.save(user);
        return password;
    }
}
