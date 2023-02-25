package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.Movie;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Проверка функций хранилища JdbcMovieRepository
 * @author nikez
 * @version $Id: $Id
 */
@SpringBootTest(classes = {JdbcMovieRepository.class})
@Import(DataSourceConfiguration.class)
class JdbcMovieRepositoryTest {
    @Autowired
    private BasicDataSource dataSource;

    /**
     * Очистка таблиц после каждого теста
     * @throws SQLException
     */
    @AfterEach
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = dataSource
                .getConnection()
                .prepareStatement("delete from movies where id>12")) {
            statement.execute();
        }
    }

    /**
     * Проверка поиска фильма по идентификатору
     */
    @Test
    public void whenFindById() {
        MovieRepository movieRepository = new JdbcMovieRepository(dataSource);
        Movie movie = movieRepository.findById(1).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(1);
        assertThat(movie.getName()).isEqualTo("непослушник2");
        assertThat(movie.getFilename()).isEqualTo("images1.jpg");
    }

    /**
     * Проверка списка всех фильмов
     */
    @Test
    public void whenFindAll() {
        MovieRepository movieRepository = new JdbcMovieRepository(dataSource);
        assertThat(movieRepository).isNotNull();

        List<Movie> movies = movieRepository.findAll();
        Movie movie = movies.get(0);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(1);
        assertThat(movie.getName()).isEqualTo("непослушник2");
        assertThat(movie.getFilename()).isEqualTo("images1.jpg");

        movie = movies.get(1);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(2);
        assertThat(movie.getName()).isEqualTo("3000 лет желаний");
        assertThat(movie.getFilename()).isEqualTo("images2.jpg");

        movie = movies.get(2);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(3);
        assertThat(movie.getName()).isEqualTo("отряд призрак");
        assertThat(movie.getFilename()).isEqualTo("images3.jpg");

        movie = movies.get(3);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(4);
        assertThat(movie.getName()).isEqualTo("русалка и дочь короля");
        assertThat(movie.getFilename()).isEqualTo("images4.jpg");

        movie = movies.get(4);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(5);
        assertThat(movie.getName()).isEqualTo("бывшие");
        assertThat(movie.getFilename()).isEqualTo("images5.jpg");

        movie = movies.get(5);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(6);
        assertThat(movie.getName()).isEqualTo("чук и гек большие приключения");
        assertThat(movie.getFilename()).isEqualTo("images6.jpg");

        movie = movies.get(6);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(7);
        assertThat(movie.getName()).isEqualTo("паранормальные явления. дом призраков");
        assertThat(movie.getFilename()).isEqualTo("images7.jpg");

        movie = movies.get(7);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(8);
        assertThat(movie.getName()).isEqualTo("вышка");
        assertThat(movie.getFilename()).isEqualTo("images8.jpg");

        movie = movies.get(8);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(9);
        assertThat(movie.getName()).isEqualTo("мира. 2 семьи. космос не расстояние");
        assertThat(movie.getFilename()).isEqualTo("images9.jpg");

        movie = movies.get(9);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(10);
        assertThat(movie.getName()).isEqualTo("тень. взять гордея");
        assertThat(movie.getFilename()).isEqualTo("images10.jpg");

        movie = movies.get(10);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(11);
        assertThat(movie.getName()).isEqualTo("астрал. реинкарнация");
        assertThat(movie.getFilename()).isEqualTo("images11.jpg");

        movie = movies.get(11);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(12);
        assertThat(movie.getName()).isEqualTo("на ощупь");
        assertThat(movie.getFilename()).isEqualTo("images12.jpg");
    }

    /**
     * Проверка добавления фильма в хранилище и
     * поиска добавленного фильма по идентификатору
     */
    @Test
    public void whenAddAndFindById() {
        MovieRepository movieRepository = new JdbcMovieRepository(dataSource);
        assertThat(movieRepository).isNotNull();

        Movie newMovie = new Movie(0, "пятый элемент","images13.jpg");
        assertThat(newMovie).isNotNull();
        assertThat(newMovie.getId()).isEqualTo(0);
        assertThat(newMovie.getName()).isEqualTo("пятый элемент");
        assertThat(newMovie.getFilename()).isEqualTo("images13.jpg");

        List<Movie> movies = movieRepository.findAll();
        assertThat(movies).isNotNull();
        int count = movies.size();

        newMovie = movieRepository.add(newMovie).orElse(null);
        assertThat(newMovie).isNotNull();
        assertThat(newMovie.getId()).isNotEqualTo(0);
        assertThat(newMovie.getName()).isEqualTo("пятый элемент");
        assertThat(newMovie.getFilename()).isEqualTo("images13.jpg");

        Movie movie = movieRepository.findById(newMovie.getId()).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(newMovie.getId());
        assertThat(movie.getName()).isEqualTo("пятый элемент");
        assertThat(movie.getFilename()).isEqualTo("images13.jpg");

        movies = movieRepository.findAll();
        assertThat(movies).isNotNull();
        assertThat(movies.size()).isEqualTo(count + 1);
    }
}