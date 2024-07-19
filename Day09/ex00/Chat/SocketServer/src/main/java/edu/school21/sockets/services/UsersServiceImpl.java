package edu.school21.sockets.services;

import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import edu.school21.sockets.models.User;

@Component
public class UsersServiceImpl implements UsersService {
    private UsersRepository ur;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryImpl") UsersRepository ur) {
        this.ur = ur;
    }

    @Override
    public void signUp(String name, String password) {
        String encryptPassword = passwordEncoder.encode(password);
        User user = new User (null, name, encryptPassword);
        ur.save(user);
    }
}
