package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.model.Room;
import ru.job4j.cinema.model.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {JdbcSessionRepository.class, JdbcMovieRepository.class, JdbcRoomRepository.class})
@Import(DataSourceConfiguration.class)
class JdbcSessionRepositoryTest {
    @Autowired
    private  BasicDataSource dataSource;

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
                .prepareStatement("delete from sessions where id>12")) {
            statement.execute();
        }
    }

    @Test
    public void whenCreateSessionAndFindByIdAndFindAll() {
        MovieRepository movieRepository = new JdbcMovieRepository(dataSource);
        Movie movie = movieRepository.findById(1).orElse(null);
        assertThat(movie).isNotNull();
        assertThat(movie.getName()).isEqualTo("непослушник2");
        assertThat(movie.getFilename()).isEqualTo("images1.jpg");

        RoomRepository roomRepository = new JdbcRoomRepository(dataSource);
        Room room = roomRepository.findById(1).orElse(null);
        assertThat(room).isNotNull();

        LocalDateTime start = LocalDateTime.of(2023,1,1,11,0,0);
        SessionRepository sessionRepository = new JdbcSessionRepository(dataSource);
        Session session = new Session(0, "name1", movie, room, start);
        assertThat(session).isNotNull();
        assertThat(session.getName()).isEqualTo("name1");
        assertThat(session.getMovie()).isEqualTo(movie);
        assertThat(session.getRoom()).isEqualTo(room);
        assertThat(session.getStart()).isEqualTo(start);

        List<Session> sessions = sessionRepository.findAll();
        assertThat(sessions).isNotNull();
        int count = sessions.size();

        Session sessionDb1 = sessionRepository.add(session).orElse(null);
        assertThat(sessionDb1).isNotNull();
        assertThat(sessionDb1.getName()).isEqualTo("name1");
        assertThat(sessionDb1.getMovie()).isEqualTo(movie);
        assertThat(sessionDb1.getRoom()).isEqualTo(room);
        assertThat(sessionDb1.getStart()).isEqualTo(start);

        sessions = sessionRepository.findAll();
        assertThat(sessions).isNotNull();
        assertThat(sessions.size()).isEqualTo(count+1);

        Session sessionDb2 = sessionRepository.findById(sessionDb1.getId()).orElse(null);
        assertThat(sessionDb2).isNotNull();
        assertThat(sessionDb2.getId()).isEqualTo(sessionDb1.getId());
        assertThat(sessionDb2.getName()).isEqualTo("name1");
        assertThat(sessionDb2.getMovie().getId()).isEqualTo(movie.getId());
        assertThat(sessionDb2.getRoom().getId()).isEqualTo(room.getId());
        assertThat(sessionDb2.getStart()).isEqualTo(start);

        Session sessionDb3 = sessions.get(count);
        assertThat(sessionDb3).isNotNull();
        assertThat(sessionDb3.getId()).isEqualTo(sessionDb1.getId());
        assertThat(sessionDb3.getName()).isEqualTo("name1");
        assertThat(sessionDb3.getMovie().getId()).isEqualTo(movie.getId());
        assertThat(sessionDb3.getRoom().getId()).isEqualTo(room.getId());
        assertThat(sessionDb3.getStart()).isEqualTo(start);
    }
}