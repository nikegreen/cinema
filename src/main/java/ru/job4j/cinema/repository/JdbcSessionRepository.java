package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcSessionRepository implements SessionRepository {
    private static final Logger LOGGER = Logger.getLogger(JdbcSessionRepository.class);
    private static final String SQL_FIND_ALL = "SELECT * FROM sessions";
    private static final String SQL_ADD =
            "INSERT INTO sessions(name, movie_id, room_id, start) VALUES (?,?,?,?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM sessions WHERE id = ?";

    private final BasicDataSource pool;

    public JdbcSessionRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    sessions.add(createSession(it));
                }
            }
        } catch (Exception e) {
            LOGGER.error("find all user. " + e.getMessage(), e);
        }
        return sessions;
    }

    @Override
    public Optional<Session> add(Session session) {
        Optional<Session> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     SQL_ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, session.getName());
            ps.setInt(2, session.getMovie().getId());
            ps.setInt(3, session.getRoom().getId());
            ps.setTimestamp(4, Timestamp.valueOf(session.getStart()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    session.setId(id.getInt("id"));
                    result = Optional.of(session);
                }
            }
        } catch (Exception e) {
            LOGGER.error("add user=" + session + ". " + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Optional<Session> findById(int id) {
        Optional<Session> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(createSession(it));
                }
            }
        } catch (Exception e) {
            LOGGER.error("find user by id=" + id + ". " + e.getMessage(), e);
        }
        return result;
    }

    private Session createSession(ResultSet it) throws SQLException {
        return new Session(
                it.getInt("id"),
                it.getString("name"),
                new Movie(it.getInt("movie_id"), "", ""),
                new Room(it.getInt("room_id"), ""),
                it.getTimestamp("start").toLocalDateTime()
        );
    }
}
