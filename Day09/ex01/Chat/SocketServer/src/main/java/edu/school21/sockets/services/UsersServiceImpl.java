package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import edu.school21.sockets.models.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UsersServiceImpl implements UsersService {
    private UsersRepository ur;
    private MessagesRepository mr;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryImpl") UsersRepository ur, @Qualifier("messagesRepositoryImpl")MessagesRepository mr) {
        this.ur = ur;
        this.mr = mr;
    }

    @Override
    public void signUp(String name, String password) {
        String encryptPassword = passwordEncoder.encode(password);
        User user = new User (null, name, encryptPassword);
        ur.save(user);
    }

    @Override
    public User signIn(String name, String password) {
        Optional<User> userOp = (Optional<User>)ur.findByName(name);
        User user = userOp.orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public void sendMessage(User user, String message) {
        Message msg = new Message(null, user, message, LocalDateTime.now());
        mr.save(msg);
    }
}
