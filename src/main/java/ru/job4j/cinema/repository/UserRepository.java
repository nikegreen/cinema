package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> add(User user);

    void update(User user);

    Optional<User> findById(int id);

    Optional<User> findByEmailAndPassword(String email, String password);
}
