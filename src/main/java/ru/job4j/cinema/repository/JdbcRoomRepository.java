package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>JdbcMovieRepository class. Репозиторий для Кинозалов в хранилище JDBC</p>
 * @author nikez
 * @version $Id: $Id
 */
@Repository
public class JdbcRoomRepository implements RoomRepository {
    private static final Logger LOGGER = Logger.getLogger(JdbcRoomRepository.class);
    private static final String SQL_FIND_ALL = "SELECT * FROM rooms ORDER BY id";
    private static final String SQL_ADD =
            "INSERT INTO rooms(name) VALUES (?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM rooms WHERE id = ?";

    private final BasicDataSource dataSource;

    public JdbcRoomRepository(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Room>} список всех Кинозалов
     */
    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    rooms.add(createRoom(it));
                }
            }
        } catch (Exception e) {
            LOGGER.error("find all user. " + e.getMessage(), e);
        }
        return rooms;
    }

    /**
     * Добавить Кинозал в хранилище
     * @param room тип {@link ru.job4j.cinema.model.Room} добавляемый Кинозал
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Room>} результат добавления:
     * Optional.Empty - не добавлен иначе
     * Optional<Movie> Кинозал с новым идентификатором Кинозала.
     */
    @Override
    public Optional<Room> add(Room room) {
        Optional<Room> result = Optional.empty();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     SQL_ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, room.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    room.setId(id.getInt("id"));
                    result = Optional.of(room);
                }
            }
        } catch (Exception e) {
            LOGGER.error("add user=" + room + ". " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * Поиск Кинозала по идентификатору
     * @param id - идентификатор Кинозала
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Room>} результат поиска
     * Optional.Empty - не найден иначе
     * Optional<Movie> найденный Кинозал.
     */
    @Override
    public Optional<Room> findById(int id) {
        Optional<Room> result = Optional.empty();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(createRoom(it));
                }
            }
        } catch (Exception e) {
            LOGGER.error("find user by id=" + id + ". " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * Чтение кинозала из БД
     * @param it - итератор результата SQL запроса из БД
     * @return тип {@link ru.job4j.cinema.model.Room} результат Кинозал из БД
     * @throws SQLException
     */
    private Room createRoom(ResultSet it) throws SQLException {
        return new Room(
                it.getInt("id"),
                it.getString("name")
        );
    }
}
