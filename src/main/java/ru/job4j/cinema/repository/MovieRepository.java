package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Movie;
import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    List<Movie> findAll();

    Optional<Movie> add(Movie movie);

    Optional<Movie> findById(int id);
}
