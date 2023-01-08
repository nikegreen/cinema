package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;

@Repository
public class JdbcUserRepository implements UserRepository {
    private static final Logger LOGGER = Logger.getLogger(JdbcUserRepository.class);
    private static final String SQL_FIND_ALL = "SELECT * FROM users";
    private static final String SQL_ADD =
            "INSERT INTO users(username, password, email, phone) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE =
            "UPDATE users SET username=?, password=?, email=?, phone=? WHERE id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SQL_FIND_BY_EMAIL_PASSWORD =
            "SELECT * FROM users WHERE email = ? AND password = ?";

    private final BasicDataSource pool;

    public JdbcUserRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(
                            createUser(it)
                    );
                }
            }
        } catch (Exception e) {
            LOGGER.error("find all user. " + e.getMessage(), e);
        }
        return users;
    }

    @Override
    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     SQL_ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt("id"));
                    result = Optional.of(user);
                }
            }
        } catch (Exception e) {
            LOGGER.error("add user=" + user + ". " + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     SQL_UPDATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setInt(5, user.getId());
            ps.execute();
        } catch (Exception e) {
            LOGGER.error("Update user=" + user + ". " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> findById(int id) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(createUser(it));
                }
            }
        } catch (Exception e) {
            LOGGER.error("find user by id=" + id + ". " + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_BY_EMAIL_PASSWORD)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(createUser(it));
                }
            }
        } catch (Exception e) {
            LOGGER.error(
                    "find user by email=" + email
                            + " and password = " + password
                            + ". " + e.getMessage(), e);
        }
        return result;
    }

    private User createUser(ResultSet it) throws SQLException {
        return new User(
                it.getInt("id"),
                it.getString("username"),
                it.getString("password"),
                it.getString("email"),
                it.getString("phone")
        );
    }
}
