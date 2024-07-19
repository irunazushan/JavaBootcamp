package edu.school21.sockets.repositories;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

public interface UsersRepository<T> extends CrudRepository<T> {
    Optional<T> findByName(String name);
}
