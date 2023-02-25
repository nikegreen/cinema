package ru.job4j.cinema.service;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.JdbcUserRepository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Проверка функций сервиса пользователей - UserServiceTest
 * @author nikez
 * @version $Id: $Id
 */
@SpringBootTest(classes = {JdbcUserRepository.class, UserService.class})
@Import(DataSourceConfiguration.class)
class UserServiceTest {
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
                .prepareStatement("delete from users where id>2")) {
            statement.execute();
        }
    }

    /**
     * Проверка добавления пользователя в хранилище и
     * поиска добавленного пользователя по идентификатору
     */
    @Test
    public void whenCreateUserAndFindById() {
        JdbcUserRepository userRepository = new JdbcUserRepository(dataSource);
        UserService store = new UserService(userRepository);
        User user = new User(0, "name", "1234", "m1@mail.ru", "+79998886655");
        user = store.add(user).orElse(null);
        User userInDb = store.findById(user.getId()).orElse(null);
        assertThat(userInDb.getId()).isEqualTo(user.getId());
        assertThat(userInDb.getEmail()).isEqualTo(user.getEmail());
        assertThat(userInDb.getPassword()).isEqualTo(user.getPassword());
        assertThat(userInDb.getUsername()).isEqualTo(user.getUsername());
        assertThat(userInDb.getPhone()).isEqualTo(user.getPhone());
    }

    /**
     * Проверка добавления 2х пользователей в хранилище и
     * поиска добавленных пользователей по идентификатору
     */
    @Test
    public void whenCreate2UserAndFindById() {
        JdbcUserRepository userRepository = new JdbcUserRepository(dataSource);
        UserService store = new UserService(userRepository);
        User user1 = new User(0, "name1", "1234", "m1@mail.ru", "+79998887766");
        user1 = store.add(user1).orElse(null);
        User userInDb1 = store.findById(user1.getId()).orElse(null);
        assertThat(userInDb1.getId()).isEqualTo(user1.getId());
        assertThat(userInDb1.getEmail()).isEqualTo(user1.getEmail());
        assertThat(userInDb1.getPassword()).isEqualTo(user1.getPassword());
        assertThat(userInDb1.getUsername()).isEqualTo(user1.getUsername());
        assertThat(userInDb1.getPhone()).isEqualTo(user1.getPhone());

        User user2 = new User(0, "name2", "1234", "m2@mail.ru", "+79998887755");
        user2 = store.add(user2).orElse(null);
        User userInDb2 = store.findById(user2.getId()).orElse(null);
        assertThat(userInDb2.getId()).isEqualTo(user2.getId());
        assertThat(userInDb2.getEmail()).isEqualTo(user2.getEmail());
        assertThat(userInDb2.getPassword()).isEqualTo(user2.getPassword());
        assertThat(userInDb2.getUsername()).isEqualTo(user2.getUsername());
        assertThat(userInDb2.getPhone()).isEqualTo(user2.getPhone());
    }

    /**
     * Проверка добавления 2х пользователей в хранилище и
     * поиска всех пользователей
     */
    @Test
    public void whenCreate2UserAndFindAll() {
        JdbcUserRepository userRepository = new JdbcUserRepository(dataSource);
        UserService store = new UserService(userRepository);
        int count = store.findAll().size();
        User user1 = new User(0, "name1", "1234", "m1@mail.ru", "+79998887766");
        user1 = store.add(user1).orElse(null);
        User user2 = new User(0, "name2", "1234", "m2@mail.ru", "+79998887755");
        user2 = store.add(user2).orElse(null);
        List<User> list = store.findAll();
        User userInDb1 = list.get(count);
        assertThat(userInDb1.getId()).isEqualTo(user1.getId());
        assertThat(userInDb1.getEmail()).isEqualTo(user1.getEmail());
        assertThat(userInDb1.getPassword()).isEqualTo(user1.getPassword());
        assertThat(userInDb1.getUsername()).isEqualTo(user1.getUsername());
        assertThat(userInDb1.getPhone()).isEqualTo(user1.getPhone());
        User userInDb2 = list.get(++count);
        assertThat(userInDb2.getId()).isEqualTo(user2.getId());
        assertThat(userInDb2.getEmail()).isEqualTo(user2.getEmail());
        assertThat(userInDb2.getPassword()).isEqualTo(user2.getPassword());
        assertThat(userInDb2.getUsername()).isEqualTo(user2.getUsername());
        assertThat(userInDb2.getPhone()).isEqualTo(user2.getPhone());
    }

    /**
     * Проверка добавления 2х пользователей
     * с одинаковыми номерами телефонов в хранилище
     */
    @Test
    public void whenCreate2UserEqualPhoneAndFindAll() {
        JdbcUserRepository userRepository = new JdbcUserRepository(dataSource);
        UserService store = new UserService(userRepository);
        int count = store.findAll().size();
        User user1 = new User(0, "name1", "1234", "m1@mail.ru", "+79998887766");
        user1 = store.add(user1).orElse(null);
        User user2 = new User(0, "name2", "1234", "m2@mail.ru", "+79998887766");
        user2 = store.add(user2).orElse(null);
        List<User> list = store.findAll();
        User userInDb1 = list.get(count);
        assertThat(userInDb1.getId()).isEqualTo(user1.getId());
        assertThat(userInDb1.getEmail()).isEqualTo(user1.getEmail());
        assertThat(userInDb1.getPassword()).isEqualTo(user1.getPassword());
        assertThat(userInDb1.getUsername()).isEqualTo(user1.getUsername());
        assertThat(userInDb1.getPhone()).isEqualTo(user1.getPhone());
        assertThat(user2).isNull();
        assertThat(list.size()).isEqualTo(++count);
    }

    /**
     * Проверка добавления 2х пользователей
     * с одинаковыми адресами электронной почты в хранилище и
     */
    @Test
    public void whenCreate2UserEqualEmailAndFindAll() {
        JdbcUserRepository userRepository = new JdbcUserRepository(dataSource);
        UserService store = new UserService(userRepository);
        int count = store.findAll().size();
        User user1 = new User(0, "name1", "1234", "m1@mail.ru", "+79998887766");
        user1 = store.add(user1).orElse(null);
        User user2 = new User(0, "name2", "1234", "m1@mail.ru", "+79998887755");
        user2 = store.add(user2).orElse(null);
        List<User> list = store.findAll();
        User userInDb1 = list.get(count);
        assertThat(userInDb1.getId()).isEqualTo(user1.getId());
        assertThat(userInDb1.getEmail()).isEqualTo(user1.getEmail());
        assertThat(userInDb1.getPassword()).isEqualTo(user1.getPassword());
        assertThat(userInDb1.getUsername()).isEqualTo(user1.getUsername());
        assertThat(userInDb1.getPhone()).isEqualTo(user1.getPhone());
        assertThat(user2).isNull();
        assertThat(list.size()).isEqualTo(++count);
    }

    /**
     * Проверка добавления 2х пользователей в хранилище и
     * поиска добавленных пользователей по идентификатору
     * и обновление пользователей в хранилище
     */
    @Test
    public void whenCreate2PostAndFindByIdAndUpdate() {
        JdbcUserRepository userRepository = new JdbcUserRepository(dataSource);
        UserService store = new UserService(userRepository);
        User user1 = new User(0, "name1", "1234", "m1@mail.ru", "+79998887766");
        user1 = store.add(user1).orElse(null);
        User userInDb1 = store.findById(user1.getId()).orElse(null);
        assertThat(userInDb1.getId()).isEqualTo(user1.getId());
        assertThat(userInDb1.getEmail()).isEqualTo(user1.getEmail());
        assertThat(userInDb1.getPassword()).isEqualTo(user1.getPassword());
        assertThat(userInDb1.getUsername()).isEqualTo(user1.getUsername());
        assertThat(userInDb1.getPhone()).isEqualTo(user1.getPhone());
        User user2 = new User(0, "name2", "1234", "m2@mail.ru", "+78889996677");
        user2 = store.add(user2).orElse(null);
        User userInDb2 = store.findById(user2.getId()).orElse(null);
        assertThat(userInDb2.getId()).isEqualTo(user2.getId());
        assertThat(userInDb2.getEmail()).isEqualTo(user2.getEmail());
        assertThat(userInDb2.getPassword()).isEqualTo(user2.getPassword());
        assertThat(userInDb2.getUsername()).isEqualTo(user2.getUsername());
        assertThat(userInDb2.getPhone()).isEqualTo(user2.getPhone());
        user1.setEmail("new1@mail.ru");
        user1.setPassword("new12345");
        user1.setUsername("newName1");
        user1.setPhone("+7987654321");
        store.update(user1);
        userInDb1 = store.findById(user1.getId()).orElse(null);
        assertThat(userInDb1.getId()).isEqualTo(user1.getId());
        assertThat(userInDb1.getEmail()).isEqualTo(user1.getEmail());
        assertThat(userInDb1.getPassword()).isEqualTo(user1.getPassword());
        assertThat(userInDb1.getUsername()).isEqualTo(user1.getUsername());
        assertThat(userInDb1.getPhone()).isEqualTo(user1.getPhone());
        user2.setEmail("new2@mail.ru");
        user2.setPassword("new12345");
        user2.setUsername("newName2");
        user2.setPhone("+79988776655");
        store.update(user2);
        userInDb2 = store.findById(user2.getId()).orElse(null);
        assertThat(userInDb2.getId()).isEqualTo(user2.getId());
        assertThat(userInDb2.getEmail()).isEqualTo(user2.getEmail());
        assertThat(userInDb2.getPassword()).isEqualTo(user2.getPassword());
        assertThat(userInDb2.getUsername()).isEqualTo(user2.getUsername());
        assertThat(userInDb2.getPhone()).isEqualTo(user2.getPhone());
    }

    /**
     * Проверка добавления 2х одинаковых пользователей в хранилище и
     * поиска добавленных пользователей по идентификатору
     */
    @Test
    public void whenCreate2EqualUserAndFindById() {
        JdbcUserRepository userRepository = new JdbcUserRepository(dataSource);
        UserService store = new UserService(userRepository);
        User user1 = new User(0, "name1", "1234", "m1@mail.ru", "+79998887766");
        user1 = store.add(user1).orElse(null);
        User userInDb1 = store.findById(user1.getId()).orElse(null);
        assertThat(userInDb1.getId()).isEqualTo(user1.getId());
        assertThat(userInDb1.getEmail()).isEqualTo(user1.getEmail());
        assertThat(userInDb1.getPassword()).isEqualTo(user1.getPassword());
        assertThat(userInDb1.getUsername()).isEqualTo(user1.getUsername());
        User user2 = new User(0, "name1", "1234", "m1@mail.ru", "+79988777666");
        user2 = store.add(user2).orElse(null);
        assertThat(user2).isNull();
    }
}