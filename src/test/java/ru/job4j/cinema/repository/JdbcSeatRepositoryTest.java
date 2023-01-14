package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.Seat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Configuration
@PropertySource("classpath:db.properties")
class JdbcSeatRepositoryTest {
    private static BasicDataSource dataSource;

    @BeforeAll
    public static void initConnection(@Value("${jdbc.driver}") String driver,
                                      @Value("${jdbc.url}") String url,
                                      @Value("${jdbc.username}") String username,
                                      @Value("${jdbc.password}") String password) {
        dataSource = new DataSourceConfiguration().loadPool(driver, url, username, password);
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        dataSource.close();
    }

    @Test
    public void whenGetByRoomId() {
        SeatRepository seatRepository = new JdbcSeatRepository(dataSource);
        final List<Seat> row1 = new ArrayList<>();
        row1.add(new Seat(1, 1, 1, 1, false));
        row1.add(new Seat(2, 1, 1, 2, false));
        row1.add(new Seat(3, 1, 1, 3, false));
        row1.add(new Seat(4, 1, 1, 4, false));
        row1.add(new Seat(5, 1, 1, 5, false));
        row1.add(new Seat(6, 1, 1, 6, false));

        final List<Seat> row2 = new ArrayList<>();
        row2.add(new Seat(7, 1, 2, 1, false));
        row2.add(new Seat(8, 1, 2, 2, false));
        row2.add(new Seat(9, 1, 2, 3, false));
        row2.add(new Seat(10, 1, 2, 4, false));
        row2.add(new Seat(11, 1, 2, 5, false));
        row2.add(new Seat(12, 1, 2, 6, false));

        final List<Seat> row3 = new ArrayList<>();
        row3.add(new Seat(13, 1, 3, 1, false));
        row3.add(new Seat(14, 1, 3, 2, false));
        row3.add(new Seat(15, 1, 3, 3, false));
        row3.add(new Seat(16, 1, 3, 4, false));
        row3.add(new Seat(17, 1, 3, 5, false));
        row3.add(new Seat(18, 1, 3, 6, false));

        final List<Seat> row4 = new ArrayList<>();
        row4.add(new Seat(19, 1, 4, 1, false));
        row4.add(new Seat(20, 1, 4, 2, false));
        row4.add(new Seat(21, 1, 4, 3, false));
        row4.add(new Seat(22, 1, 4, 4, false));
        row4.add(new Seat(23, 1, 4, 5, false));
        row4.add(new Seat(24, 1, 4, 6, false));
        row4.add(new Seat(25, 1, 4, 7, false));
        row4.add(new Seat(26, 1, 4, 8, false));

        final List<Seat> row5 = new ArrayList<>();
        row5.add(new Seat(27, 1, 5, 1, false));
        row5.add(new Seat(28, 1, 5, 2, false));
        row5.add(new Seat(29, 1, 5, 3, false));
        row5.add(new Seat(30, 1, 5, 4, false));
        row5.add(new Seat(31, 1, 5, 5, false));
        row5.add(new Seat(32, 1, 5, 6, false));
        row5.add(new Seat(33, 1, 5, 7, false));
        row5.add(new Seat(34, 1, 5, 8, false));

        final List<Seat> row6 = new ArrayList<>();
        row6.add(new Seat(35, 1, 6, 1, false));
        row6.add(new Seat(36, 1, 6, 2, false));
        row6.add(new Seat(37, 1, 6, 3, false));
        row6.add(new Seat(38, 1, 6, 4, false));
        row6.add(new Seat(39, 1, 6, 5, false));
        row6.add(new Seat(40, 1, 6, 6, false));
        row6.add(new Seat(41, 1, 6, 7, false));
        row6.add(new Seat(42, 1, 6, 8, false));

        List<List<Seat>> testSeats = new ArrayList<>();
        testSeats.add(row1);
        testSeats.add(row2);
        testSeats.add(row3);
        testSeats.add(row4);
        testSeats.add(row5);
        testSeats.add(row6);

        List<List<Seat>> seats = seatRepository.getByRoomId(1);
        assertThat(seats).isNotNull();
        assertThat(seats).isEqualTo(testSeats);
    }

    @Test
    public void whenGetByRoomIdAndRow() {
        SeatRepository seatRepository = new JdbcSeatRepository(dataSource);
        final List<Seat> row1 = new ArrayList<>();
        row1.add(new Seat(1, 1, 1, 1, false));
        row1.add(new Seat(2, 1, 1, 2, false));
        row1.add(new Seat(3, 1, 1, 3, false));
        row1.add(new Seat(4, 1, 1, 4, false));
        row1.add(new Seat(5, 1, 1, 5, false));
        row1.add(new Seat(6, 1, 1, 6, false));

        final List<Seat> row2 = new ArrayList<>();
        row2.add(new Seat(7, 1, 2, 1, false));
        row2.add(new Seat(8, 1, 2, 2, false));
        row2.add(new Seat(9, 1, 2, 3, false));
        row2.add(new Seat(10, 1, 2, 4, false));
        row2.add(new Seat(11, 1, 2, 5, false));
        row2.add(new Seat(12, 1, 2, 6, false));

        final List<Seat> row3 = new ArrayList<>();
        row3.add(new Seat(13, 1, 3, 1, false));
        row3.add(new Seat(14, 1, 3, 2, false));
        row3.add(new Seat(15, 1, 3, 3, false));
        row3.add(new Seat(16, 1, 3, 4, false));
        row3.add(new Seat(17, 1, 3, 5, false));
        row3.add(new Seat(18, 1, 3, 6, false));

        final List<Seat> row4 = new ArrayList<>();
        row4.add(new Seat(19, 1, 4, 1, false));
        row4.add(new Seat(20, 1, 4, 2, false));
        row4.add(new Seat(21, 1, 4, 3, false));
        row4.add(new Seat(22, 1, 4, 4, false));
        row4.add(new Seat(23, 1, 4, 5, false));
        row4.add(new Seat(24, 1, 4, 6, false));
        row4.add(new Seat(25, 1, 4, 7, false));
        row4.add(new Seat(26, 1, 4, 8, false));

        final List<Seat> row5 = new ArrayList<>();
        row5.add(new Seat(27, 1, 5, 1, false));
        row5.add(new Seat(28, 1, 5, 2, false));
        row5.add(new Seat(29, 1, 5, 3, false));
        row5.add(new Seat(30, 1, 5, 4, false));
        row5.add(new Seat(31, 1, 5, 5, false));
        row5.add(new Seat(32, 1, 5, 6, false));
        row5.add(new Seat(33, 1, 5, 7, false));
        row5.add(new Seat(34, 1, 5, 8, false));

        final List<Seat> row6 = new ArrayList<>();
        row6.add(new Seat(35, 1, 6, 1, false));
        row6.add(new Seat(36, 1, 6, 2, false));
        row6.add(new Seat(37, 1, 6, 3, false));
        row6.add(new Seat(38, 1, 6, 4, false));
        row6.add(new Seat(39, 1, 6, 5, false));
        row6.add(new Seat(40, 1, 6, 6, false));
        row6.add(new Seat(41, 1, 6, 7, false));
        row6.add(new Seat(42, 1, 6, 8, false));

        List<Seat> seats1 = seatRepository.getByRoomIdAndRow(1, 1);
        assertThat(seats1).isNotNull();
        assertThat(seats1).isEqualTo(row1);

        List<Seat> seats2 = seatRepository.getByRoomIdAndRow(1, 2);
        assertThat(seats2).isNotNull();
        assertThat(seats2).isEqualTo(row2);

        List<Seat> seats3 = seatRepository.getByRoomIdAndRow(1, 3);
        assertThat(seats3).isNotNull();
        assertThat(seats3).isEqualTo(row3);

        List<Seat> seats4 = seatRepository.getByRoomIdAndRow(1, 4);
        assertThat(seats4).isNotNull();
        assertThat(seats4).isEqualTo(row4);

        List<Seat> seats5 = seatRepository.getByRoomIdAndRow(1, 5);
        assertThat(seats5).isNotNull();
        assertThat(seats5).isEqualTo(row5);

        List<Seat> seats6 = seatRepository.getByRoomIdAndRow(1, 6);
        assertThat(seats6).isNotNull();
        assertThat(seats6).isEqualTo(row6);
    }
}