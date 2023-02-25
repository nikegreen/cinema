package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Optional;

/**
 * <p>UserRepository class. Репозиторий для Пользователей (интерфейс хранилища)</p>
 * @author nikez
 * @version $Id: $Id
 */
public interface UserRepository {
    /**
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Room>} список всех Пользователей
     */
    List<User> findAll();

    /**
     * Добавить Пользователя в хранилище
     * @param user тип {@link ru.job4j.cinema.model.User} добавляемый Пользователь
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.User>} результат добавления:
     * Optional.Empty - Пользователь не добавлен иначе
     * Optional<User> Пользователь с новым идентификатором.
     */
    Optional<User> add(User user);

    /**
     * Обновить хранилище данными из:
     * @param user - Пользователь
     */
    void update(User user);

    /**
     * Поиск Пользователя по идентификатору
     * @param id - идентификатор Пользователя
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.User>} результат поиска
     * Optional.Empty - не найден иначе
     * Optional<User> найденный Пользователь.
     */
    Optional<User> findById(int id);

    /**
     * Поиск Пользователя по:
     * @param email - адрес электронной почты Пользователя
     * @param password - пароль Пользователя
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.User>} результат поиска
     * Optional.Empty - не найден иначе
     * Optional<User> найденный Пользователь.
     */
    Optional<User> findByEmailAndPassword(String email, String password);
}
