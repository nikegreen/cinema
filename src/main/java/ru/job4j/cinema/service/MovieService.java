package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.repository.MovieRepository;
import java.util.List;
import java.util.Optional;

/**
 * <p>MovieService class. Сервис для Кинофильмов в хранилище</p>
 * @author nikez
 * @version $Id: $Id
 */
@ThreadSafe
@Service
public class MovieService {
    private final MovieRepository store;

    /**
     * Конструктор сервиса
     * @param store - хранилище кинофильмов
     */
    public MovieService(MovieRepository store) {
        this.store = store;
    }

    /**
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Movie>} список всех фильмов
     */
    public List<Movie> findAll() {
        return store.findAll();
    }

    /**
     * Добавить фильм в хранилище
     * @param movie тип {@link ru.job4j.cinema.model.Movie} добавляемый фильм
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Movie>} результат добавления:
     * Optional.Empty - не добавлен иначе
     * Optional<Movie> фильм с новым идентификатором фильма.
     */
    public Optional<Movie> add(Movie movie) {
        return store.add(movie);
    }

    /**
     * Поиск фильма по идентификатору
     * @param id - идентификатор фильма
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Movie>} результат поиска
     * Optional.Empty - не найден иначе
     * Optional<Movie> найденный фильм.
     */
    public Optional<Movie> findById(int id) {
        return store.findById(id);
    }
}
