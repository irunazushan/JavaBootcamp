package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private final UsersRepository ur = mock(UsersRepository.class);
    private final String login = "user1";
    private final String password = "password1";
    private final User user = new User(1L, login, password);

    @BeforeEach
    public void setup(){
        when(ur.findByLogin(login)).thenReturn(user);
        doNothing().when(ur).update(user);
    }

    @Test
    public void correctLoginPasswordShouldReturnTrue() {
        UsersServiceImpl us = new UsersServiceImpl(ur);
        Assertions.assertTrue(us.authenticate(login,password));
    }

    @Test
    public void AlreadyAuthenticatedExceptionShouldBeThrown() {
        user.setAuthenticationStatus(true);

        UsersServiceImpl us = new UsersServiceImpl(ur);
        Assertions.assertThrows(
                AlreadyAuthenticatedException.class,
                () -> us.authenticate(login,password));
    }

    @Test
    public void incorrectLoginShouldThrowError() {
        when(ur.findByLogin(login)).thenThrow(RuntimeException.class);

        UsersServiceImpl us = new UsersServiceImpl(ur);
        Assertions.assertThrows(
                RuntimeException.class,
                () -> us.authenticate(login,password));
    }

    @Test
    public void incorrectPasswordShouldThrowError() {
        UsersServiceImpl us = new UsersServiceImpl(ur);
        String incorrectPassword = "incorrect";
        Assertions.assertFalse(us.authenticate(login,incorrectPassword));

    }
}
