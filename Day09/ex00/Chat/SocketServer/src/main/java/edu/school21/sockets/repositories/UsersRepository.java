package edu.school21.sockets.repositories;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UsersRepository<T> extends CrudRepository<T> {
    Optional<T> findByName(String name);
}
