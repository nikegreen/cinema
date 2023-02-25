package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Movie;
import java.util.List;
import java.util.Optional;

/**
 * <p>JdbcMovieRepository class. Репозиторий для Кинофильмов (интерфейс хранилища)</p>
 * @author nikez
 * @version $Id: $Id
 */
public interface MovieRepository {
    /**
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Movie>} список всех фильмов
     */
    List<Movie> findAll();

    /**
     * Добавить фильм в хранилище
     * @param movie тип {@link ru.job4j.cinema.model.Movie} добавляемый фильм
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Movie>} результат добавления:
     * Optional.Empty - не добавлен иначе
     * Optional<Movie> фильм с новым идентификатором фильма.
     */
    Optional<Movie> add(Movie movie);

    /**
     * Поиск фильма по идентификатору
     * @param id - идентификатор фильма
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Movie>} результат поиска
     * Optional.Empty - не найден иначе
     * Optional<Movie> найденный фильм.
     */
    Optional<Movie> findById(int id);
}
