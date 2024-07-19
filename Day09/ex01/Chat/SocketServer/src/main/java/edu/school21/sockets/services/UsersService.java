package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface UsersService {
    void signUp(String name, String password);
    User signIn(String name, String password);
    void sendMessage(User user, String message);
}
