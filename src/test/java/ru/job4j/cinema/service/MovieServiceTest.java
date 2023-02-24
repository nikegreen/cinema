package ru.job4j.cinema.service;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.repository.JdbcMovieRepository;
import ru.job4j.cinema.repository.MovieRepository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {JdbcMovieRepository.class})
@Import(DataSourceConfiguration.class)
class MovieServiceTest {
    @Autowired
    private BasicDataSource dataSource;

//    @BeforeAll
//    public static void initConnection(@Value("${jdbc.driver}") String driver,
//                                      @Value("${jdbc.url}") String url,
//                                      @Value("${jdbc.username}") String username,
//                                      @Value("${jdbc.password}") String password) {
//        dataSource = new DataSourceConfiguration().loadPool(driver, url, username, password);
//    }
//
//    @AfterAll
//    public static void closeConnection() throws SQLException {
//        dataSource.close();
//    }

    @AfterEach
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = dataSource
                .getConnection()
                .prepareStatement("delete from movies where id>12")) {
            statement.execute();
        }
    }

    @Test
    public void whenFindById() {
        MovieRepository movieRepository = new JdbcMovieRepository(dataSource);
        assertThat(movieRepository).isNotNull();
        MovieService movieService = new MovieService(movieRepository);
        assertThat(movieService).isNotNull();

        Movie movie = movieService.findById(1).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(1);
        assertThat(movie.getName()).isEqualTo("непослушник2");
        assertThat(movie.getFilename()).isEqualTo("images1.jpg");

        movie = movieService.findById(2).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(2);
        assertThat(movie.getName()).isEqualTo("3000 лет желаний");
        assertThat(movie.getFilename()).isEqualTo("images2.jpg");

        movie = movieService.findById(3).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(3);
        assertThat(movie.getName()).isEqualTo("отряд призрак");
        assertThat(movie.getFilename()).isEqualTo("images3.jpg");

        movie = movieService.findById(4).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(4);
        assertThat(movie.getName()).isEqualTo("русалка и дочь короля");
        assertThat(movie.getFilename()).isEqualTo("images4.jpg");

        movie = movieService.findById(5).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(5);
        assertThat(movie.getName()).isEqualTo("бывшие");
        assertThat(movie.getFilename()).isEqualTo("images5.jpg");

        movie = movieService.findById(6).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(6);
        assertThat(movie.getName()).isEqualTo("чук и гек большие приключения");
        assertThat(movie.getFilename()).isEqualTo("images6.jpg");

        movie = movieService.findById(7).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(7);
        assertThat(movie.getName()).isEqualTo("паранормальные явления. дом призраков");
        assertThat(movie.getFilename()).isEqualTo("images7.jpg");

        movie = movieService.findById(8).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(8);
        assertThat(movie.getName()).isEqualTo("вышка");
        assertThat(movie.getFilename()).isEqualTo("images8.jpg");

        movie = movieService.findById(9).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(9);
        assertThat(movie.getName()).isEqualTo("мира. 2 семьи. космос не расстояние");
        assertThat(movie.getFilename()).isEqualTo("images9.jpg");

        movie = movieService.findById(10).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(10);
        assertThat(movie.getName()).isEqualTo("тень. взять гордея");
        assertThat(movie.getFilename()).isEqualTo("images10.jpg");

        movie = movieService.findById(11).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(11);
        assertThat(movie.getName()).isEqualTo("астрал. реинкарнация");
        assertThat(movie.getFilename()).isEqualTo("images11.jpg");

        movie = movieService.findById(12).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(12);
        assertThat(movie.getName()).isEqualTo("на ощупь");
        assertThat(movie.getFilename()).isEqualTo("images12.jpg");
    }

    @Test
    public void whenFindAll() {
        MovieRepository movieRepository = new JdbcMovieRepository(dataSource);
        assertThat(movieRepository).isNotNull();
        MovieService movieService = new MovieService(movieRepository);
        assertThat(movieService).isNotNull();

        List<Movie> movies = movieService.findAll();
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

    @Test
    public void whenAddAndFindById() {
        MovieRepository movieRepository = new JdbcMovieRepository(dataSource);
        assertThat(movieRepository).isNotNull();
        MovieService movieService = new MovieService(movieRepository);
        assertThat(movieService).isNotNull();

        Movie newMovie = new Movie(0, "пятый элемент","images13.jpg");
        assertThat(newMovie).isNotNull();
        assertThat(newMovie.getId()).isEqualTo(0);
        assertThat(newMovie.getName()).isEqualTo("пятый элемент");
        assertThat(newMovie.getFilename()).isEqualTo("images13.jpg");

        List<Movie> movies = movieService.findAll();
        assertThat(movies).isNotNull();
        int count = movies.size();

        newMovie = movieService.add(newMovie).orElse(null);
        assertThat(newMovie).isNotNull();
        assertThat(newMovie.getId()).isNotEqualTo(0);
        assertThat(newMovie.getName()).isEqualTo("пятый элемент");
        assertThat(newMovie.getFilename()).isEqualTo("images13.jpg");

        Movie movie = movieService.findById(newMovie.getId()).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(newMovie.getId());
        assertThat(movie.getName()).isEqualTo("пятый элемент");
        assertThat(movie.getFilename()).isEqualTo("images13.jpg");

        movies = movieService.findAll();
        assertThat(movies).isNotNull();
        assertThat(movies.size()).isEqualTo(count + 1);
    }
}