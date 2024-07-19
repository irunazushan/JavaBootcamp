package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private UsersRepository ur;
    public UsersServiceImpl(UsersRepository ur) {
        this.ur = ur;
    }
    boolean authenticate(String login, String password) {
        User user;
        user = ur.findByLogin(login);
        if (user.isAuthenticationStatus()) {
            throw new AlreadyAuthenticatedException();
        }
        if (user.getPassword().equals(password)) {
            user.setAuthenticationStatus(true);
            ur.update(user);
            return true;
        }

        return false;
    }
}
