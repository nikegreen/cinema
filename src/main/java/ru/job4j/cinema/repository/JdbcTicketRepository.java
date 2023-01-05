package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTicketRepository implements TicketRepository {
    private static final Logger LOGGER = Logger.getLogger(JdbcUserRepository.class);
    private static final String SQL_FIND_ALL_BY_SESSION =
            "SELECT * FROM tickets WHERE session_id = ?";
    private static final String SQL_FIND_ALL_BY_USER =
            "SELECT * FROM tickets WHERE user_id = ?";
    private static final String SQL_FIND_ALL_BY_SESSION_AND_USER =
            "SELECT * FROM tickets WHERE session_id = ? AND user_id = ?";
    private static final String SQL_ADD =
            "INSERT INTO tickets(session_id, pos_row, cell, user_id) VALUES (?,?,?,?)";
    //private static final String SQL_UPDATE =
    //        "UPDATE tickets SET session_id=?, pos_row=?, cell=?, user_id=? WHERE id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM tickets WHERE id = ?";

    //private static final String SQL_FIND_BY_EMAIL_PASSWORD =
    //        "SELECT * FROM tickets WHERE email = ? AND password = ?";

    private final BasicDataSource pool;

    public JdbcTicketRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public List<Ticket> findAllBySession(int sessionId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_ALL_BY_SESSION)
        ) {
            ps.setInt(1, sessionId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(
                            createTicket(it)
                    );
                }
            }
        } catch (Exception e) {
            LOGGER.error("find all user. " + e.getMessage(), e);
        }
        return tickets;
    }

    @Override
    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     SQL_ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSession().getId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUser().getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                    result = Optional.of(ticket);
                }
            }
        } catch (Exception e) {
            LOGGER.error("add user=" + ticket + ". " + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Optional<Ticket> findById(int id) {
        Optional<Ticket> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(createTicket(it));
                }
            }
        } catch (Exception e) {
            LOGGER.error("find user by id=" + id + ". " + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<Ticket> findAllByUser(int userId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_ALL_BY_USER)
        ) {
            ps.setInt(1, userId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(
                            createTicket(it)
                    );
                }
            }
        } catch (Exception e) {
            LOGGER.error("find all tickets where user id =" + userId + ". " + e.getMessage(), e);
        }
        return tickets;
    }

    @Override
    public List<Ticket> findAllBySessionAndUser(int sessionId, int userId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_ALL_BY_SESSION_AND_USER)
        ) {
            ps.setInt(1, sessionId);
            ps.setInt(2, userId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(
                            createTicket(it)
                    );
                }
            }
        } catch (Exception e) {
            LOGGER.error("find all tickets where user id =" + userId
                    + " and session id=" + sessionId + ". "
                    + e.getMessage(), e);
        }
        return tickets;
    }

    private Ticket createTicket(ResultSet it) throws SQLException {
        return new Ticket(
                it.getInt("id"),
                new Session(it.getInt("session_id"), "", null, null, null),
                it.getInt("pos_row"),
                it.getInt("cell"),
                new User(it.getInt("user_id"), "", "", "", "")
        );
    }
}
