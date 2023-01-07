package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import ru.job4j.cinema.*;
import ru.job4j.cinema.model.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

class JdbcUserRepositoryTest {
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
                .prepareStatement("delete from users where id>2")) {
            statement.execute();
        }
    }

    @Test
    public void whenCreateUserAndFindById() {
        UserRepository store = new JdbcUserRepository(dataSource);
        User user = new User(0, "name", "1234", "m1@mail.ru", "+79998886655");
        user = store.add(user).orElse(null);
        User userInDb = store.findById(user.getId()).orElse(null);
        assertThat(userInDb.getId()).isEqualTo(user.getId());
        assertThat(userInDb.getEmail()).isEqualTo(user.getEmail());
        assertThat(userInDb.getPassword()).isEqualTo(user.getPassword());
        assertThat(userInDb.getUsername()).isEqualTo(user.getUsername());
        assertThat(userInDb.getPhone()).isEqualTo(user.getPhone());
    }

    @Test
    public void whenCreate2UserAndFindById() {
        UserRepository store = new JdbcUserRepository(dataSource);
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

    @Test
    public void whenCreate2UserAndFindAll() {
        UserRepository store = new JdbcUserRepository(dataSource);
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

    @Test
    public void whenCreate2UserEqualPhoneAndFindAll() {
        UserRepository store = new JdbcUserRepository(dataSource);
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

    @Test
    public void whenCreate2UserEqualEmailAndFindAll() {
        UserRepository store = new JdbcUserRepository(dataSource);
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

    @Test
    public void whenCreate2PostAndFindByIdAndUpdate() {
        UserRepository store = new JdbcUserRepository(dataSource);
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

    @Test
    public void whenCreate2EqualUserAndFindById() {
        UserRepository store = new JdbcUserRepository(dataSource);
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