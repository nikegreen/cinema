package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Room;

import java.util.List;
import java.util.Optional;

/**
 * <p>JdbcMovieRepository class. Репозиторий для Кинозалов (интерфейс хранилища)</p>
 * @author nikez
 * @version $Id: $Id
 */
public interface RoomRepository {
    /**
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Room>} список всех Кинозалов
     */
    List<Room> findAll();

    /**
     * Добавить Кинозал в хранилище
     * @param room тип {@link ru.job4j.cinema.model.Room} добавляемый Кинозал
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Room>} результат добавления:
     * Optional.Empty - не добавлен иначе
     * Optional<Movie> Кинозал с новым идентификатором Кинозала.
     */
    Optional<Room> add(Room room);

    /**
     * Поиск Кинозала по идентификатору
     * @param id - идентификатор Кинозала
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Room>} результат поиска
     * Optional.Empty - не найден иначе
     * Optional<Movie> найденный Кинозал.
     */
    Optional<Room> findById(int id);
}
