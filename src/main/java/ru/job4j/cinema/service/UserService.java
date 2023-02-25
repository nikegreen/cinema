package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.repository.UserRepository;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.JdbcUserRepository;
import java.util.List;
import java.util.Optional;

/**
 * <p>UserService class. Сервис для Пользователей</p>
 * @author nikez
 * @version $Id: $Id
 */
@ThreadSafe
@Service
public class UserService {
    private final UserRepository store;

    public UserService(JdbcUserRepository store) {
        this.store = store;
    }

    /**
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Room>} список всех Пользователей
     */
    public List<User> findAll() {
        return store.findAll();
    }

    /**
     * Добавить Пользователя в хранилище
     * @param user тип {@link ru.job4j.cinema.model.User} добавляемый Пользователь
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.User>} результат добавления:
     * Optional.Empty - Пользователь не добавлен иначе
     * Optional<User> Пользователь с новым идентификатором.
     */
    public Optional<User> add(User user) {
        return store.add(user);
    }

    /**
     * Поиск Пользователя по идентификатору
     * @param id - идентификатор Пользователя
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.User>} результат поиска
     * Optional.Empty - не найден иначе
     * Optional<User> найденный Пользователь.
     */
    public Optional<User> findById(int id) {
        return store.findById(id);
    }

    /**
     * Обновить хранилище данными из:
     * @param user - Пользователь
     */
    public void update(User user) {
        store.update(user);
    }

    /**
     * Поиск Пользователя по:
     * @param email - адрес электронной почты Пользователя
     * @param password - пароль Пользователя
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.User>} результат поиска
     * Optional.Empty - не найден иначе
     * Optional<User> найденный Пользователь.
     */
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return store.findByEmailAndPassword(email, password);
    }
}
