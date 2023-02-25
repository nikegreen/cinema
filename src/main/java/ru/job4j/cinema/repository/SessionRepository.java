package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;
import java.util.List;
import java.util.Optional;

/**
 * <p>SessionRepository class. Репозиторий для Киносеансов (интерфейс хранилища)</p>
 * @author nikez
 * @version $Id: $Id
 */
public interface SessionRepository {
    /**
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Session>} список всех Киносеансов
     */
    List<Session> findAll();

    /**
     * Добавить Киносеанс в хранилище
     * @param session тип {@link ru.job4j.cinema.model.Session} добавляемый Киносеанс
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Session>} результат добавления:
     * Optional.Empty - не добавлен иначе
     * Optional<Movie> Киносеанс с новым идентификатором Киносеанса.
     */
    Optional<Session> add(Session session);

    /**
     * Поиск Киносеанса по идентификатору
     * @param id - идентификатор Киносеанса
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Session>} результат поиска
     * Optional.Empty - Киносеанс не найден иначе
     * Optional<Movie> найденный Киносеанс.
     */
    Optional<Session> findById(int id);
}
