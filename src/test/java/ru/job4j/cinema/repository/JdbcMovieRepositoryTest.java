package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Movie;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JdbcMovieRepositoryTest {
    private static BasicDataSource dataSource;

    @BeforeAll
    public static void initConnection() {
        dataSource = new Main().loadPool();
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        dataSource.close();
    }

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
        Movie movie = movieRepository.findById(1).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getId()).isEqualTo(1);
        assertThat(movie.getName()).isEqualTo("непослушник2");
        assertThat(movie.getFilename()).isEqualTo("images1.jpg");
    }
  
}