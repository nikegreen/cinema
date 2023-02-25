package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.Room;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Проверка функций хранилища JdbcRoomRepository
 * @author nikez
 * @version $Id: $Id
 */
@SpringBootTest(classes = {JdbcRoomRepository.class})
@Import(DataSourceConfiguration.class)
class JdbcRoomRepositoryTest {
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
                .prepareStatement("delete from rooms where id>3")) {
            statement.execute();
        }
    }

    /**
     * Проверка поиска кинозала по идентификатору
     */
    @Test
    public void whenFindById() {
        RoomRepository roomRepository = new JdbcRoomRepository(dataSource);
        Room room1 = roomRepository.findById(1).orElse(null);
        assertThat(room1).isNotNull();
        assertThat(room1.getId()).isEqualTo(1);
        assertThat(room1.getName()).isEqualTo("малый зал");
        Room room2 = roomRepository.findById(2).orElse(null);
        assertThat(room2).isNotNull();
        assertThat(room2.getId()).isEqualTo(2);
        assertThat(room2.getName()).isEqualTo("средний зал");
        Room room3 = roomRepository.findById(3).orElse(null);
        assertThat(room3).isNotNull();
        assertThat(room3.getId()).isEqualTo(3);
        assertThat(room3.getName()).isEqualTo("большой зал");
    }

    /**
     * Проверка списка всех кинозалов и
     */
    @Test
    public void whenFindByIdAndFindAll() {
        RoomRepository roomRepository = new JdbcRoomRepository(dataSource);
        List<Room> rooms = roomRepository.findAll();
        Room room1 = rooms.get(0);
        assertThat(room1).isNotNull();
        assertThat(room1.getId()).isEqualTo(1);
        assertThat(room1.getName()).isEqualTo("малый зал");
        Room room2 = rooms.get(1);
        assertThat(room2).isNotNull();
        assertThat(room2.getId()).isEqualTo(2);
        assertThat(room2.getName()).isEqualTo("средний зал");
        Room room3 = rooms.get(2);
        assertThat(room3).isNotNull();
        assertThat(room3.getId()).isEqualTo(3);
        assertThat(room3.getName()).isEqualTo("большой зал");
    }

    /**
     * Проверка добавления кинозала в хранилище и
     * поиска добавленного кинозала по идентификатору
     */
    @Test
    public void whenCreateRoomAndFindById() {
        RoomRepository roomRepository = new JdbcRoomRepository(dataSource);
        Room vipRoom = new Room(1, "vip room");
        vipRoom = roomRepository.add(vipRoom).orElse(null);
        assertThat(vipRoom).isNotNull();
        assertThat(vipRoom.getName()).isEqualTo("vip room");
        Room room2 = roomRepository.findById(vipRoom.getId()).orElse(null);
        assertThat(room2).isNotNull();
        assertThat(room2.getId()).isEqualTo(vipRoom.getId());
        assertThat(room2.getName()).isEqualTo("vip room");
        Room room3 = roomRepository.add(vipRoom).orElse(null);
        assertThat(room3).isNull();
    }
}