package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcRoomRepository implements RoomRepository {
    private static final Logger LOGGER = Logger.getLogger(JdbcRoomRepository.class);
    private static final String SQL_FIND_ALL = "SELECT * FROM rooms";
    private static final String SQL_ADD =
            "INSERT INTO rooms(name) VALUES (?)";
    //private static final String SQL_UPDATE =
    //        "UPDATE rooms SET name=? WHERE id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM rooms WHERE id = ?";

    //private static final String SQL_FIND_BY_EMAIL_PASSWORD =
    //        "SELECT * FROM rooms WHERE email = ? AND password = ?";

    private final BasicDataSource pool;

    public JdbcRoomRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        try (Connection cn = pool.getConnection();
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

    @Override
    public Optional<Room> add(Room room) {
        Optional<Room> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     SQL_ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, room.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    room.setId(id.getInt(1));
                    result = Optional.of(room);
                }
            }
        } catch (Exception e) {
            LOGGER.error("add user=" + room + ". " + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Optional<Room> findById(int id) {
        Optional<Room> result = Optional.empty();
        try (Connection cn = pool.getConnection();
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

    private Room createRoom(ResultSet it) throws SQLException {
        return new Room(
                it.getInt("id"),
                it.getString("name")
        );
    }
}
