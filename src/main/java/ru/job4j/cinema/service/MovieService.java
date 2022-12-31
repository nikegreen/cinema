package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.repository.MovieRepository;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class MovieService {
    private final MovieRepository store;

    public MovieService(MovieRepository store) {
        this.store = store;
    }

    public List<Movie> findAll() {
        return store.findAll();
    }

    public Optional<Movie> add(Movie movie) {
        return store.add(movie);
    }

    public Optional<Movie> findById(int id) {
        return store.findById(id);
    }
}
