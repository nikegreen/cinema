package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.repository.UserRepository;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.JdbcUserRepository;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class UserService {
    private final UserRepository store;

    public UserService(JdbcUserRepository store) {
        this.store = store;
    }

    public List<User> findAll() {
        return store.findAll();
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findById(int id) {
        return store.findById(id);
    }

    public void update(User user) {
        store.update(user);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return store.findByEmailAndPassword(email, password);
    }
}
