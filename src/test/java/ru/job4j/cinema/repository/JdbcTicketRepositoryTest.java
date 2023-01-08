package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class JdbcTicketRepositoryTest {
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
                .prepareStatement("delete from tickets")) {
            statement.execute();
        }
    }

    @Test
    public void whenCreateTicketAndFindById() {
        SessionRepository sessionRepository = new JdbcSessionRepository(dataSource);
        Session session = sessionRepository.findById(1).orElse(null);
        assertThat(session).isNotNull();
        assertThat(session.getId()).isEqualTo(1);
        assertThat(session.getRoom().getId()).isEqualTo(1);
        assertThat(session.getMovie().getId()).isEqualTo(1);
        assertThat(session.getStart())
                .isEqualTo(LocalDateTime.of(2023,1,8,9,5, 0));
        TicketRepository store = new JdbcTicketRepository(dataSource);
        UserRepository userRepository = new JdbcUserRepository(dataSource);
        User user = userRepository.findById(1).orElse(null);
        assertThat(user).isNotNull();
        Ticket ticket = new Ticket(1, session, 1, 1, user);
        ticket = store.add(ticket).orElse(null);
        Ticket ticket1 = store.findById(ticket.getId()).orElse(null);
        assertThat(ticket1.getId()).isEqualTo(ticket.getId());
        assertThat(ticket1.getCell()).isEqualTo(ticket.getCell());
        assertThat(ticket1.getRow()).isEqualTo(ticket.getRow());
        assertThat(ticket1.getUser()).isEqualTo(ticket.getUser());
        assertThat(ticket1.getSession()).isEqualTo(ticket.getSession());
    }

    @Test
    public void whenCreate2TicketAndFindById() {
        SessionRepository sessionRepository = new JdbcSessionRepository(dataSource);
        Session session = sessionRepository.findById(1).orElse(null);
        assertThat(session).isNotNull();
        assertThat(session.getId()).isEqualTo(1);
        assertThat(session.getRoom().getId()).isEqualTo(1);
        assertThat(session.getMovie().getId()).isEqualTo(1);
        assertThat(session.getStart())
                .isEqualTo(LocalDateTime.of(2023,1,8,9,5, 0));
        UserRepository userRepository = new JdbcUserRepository(dataSource);
        User user1 = userRepository.findById(1).orElse(null);
        assertThat(user1).isNotNull();
        TicketRepository store = new JdbcTicketRepository(dataSource);
        Ticket ticket1 = new Ticket(1, session, 1, 1, user1);
        ticket1 = store.add(ticket1).orElse(null);
        assertThat(ticket1).isNotNull();
        Ticket ticketInDb1 = store.findById(ticket1.getId()).orElse(null);
        assertThat(ticketInDb1).isNotNull();
        assertThat(ticketInDb1.getId()).isEqualTo(ticket1.getId());
        assertThat(ticketInDb1.getSession()).isEqualTo(ticket1.getSession());
        assertThat(ticketInDb1.getRow()).isEqualTo(ticket1.getRow());
        assertThat(ticketInDb1.getCell()).isEqualTo(ticket1.getCell());
        assertThat(ticketInDb1.getUser()).isEqualTo(ticket1.getUser());

        Ticket ticket2 = new Ticket(1, session, 1, 2, user1);
        ticket2 = store.add(ticket2).orElse(null);
        assertThat(ticket2).isNotNull();
        Ticket ticketInDb2 = store.findById(ticket2.getId()).orElse(null);
        assertThat(ticketInDb2).isNotNull();
        assertThat(ticketInDb2.getId()).isEqualTo(ticket2.getId());
        assertThat(ticketInDb2.getSession()).isEqualTo(ticket2.getSession());
        assertThat(ticketInDb2.getRow()).isEqualTo(ticket2.getRow());
        assertThat(ticketInDb2.getCell()).isEqualTo(ticket2.getCell());
        assertThat(ticketInDb2.getUser()).isEqualTo(ticket2.getUser());

        Ticket ticket3 = store.add(ticket1).orElse(null);
        assertThat(ticket3).isNull();
        Ticket ticketInDb3 = store.findById(ticket1.getId()).orElse(null);
        assertThat(ticketInDb3).isNotNull();
        assertThat(ticketInDb3.getId()).isEqualTo(ticket1.getId());
        assertThat(ticketInDb3.getSession()).isEqualTo(ticket1.getSession());
        assertThat(ticketInDb3.getRow()).isEqualTo(ticket1.getRow());
        assertThat(ticketInDb3.getCell()).isEqualTo(ticket1.getCell());
        assertThat(ticketInDb3.getUser()).isEqualTo(ticket1.getUser());
    }

    @Test
    public void whenCreate2TicketAndFindAllBySession() {
        SessionRepository sessionRepository = new JdbcSessionRepository(dataSource);
        Session session = sessionRepository.findById(1).orElse(null);
        assertThat(session).isNotNull();
        assertThat(session.getId()).isEqualTo(1);
        assertThat(session.getRoom().getId()).isEqualTo(1);
        assertThat(session.getMovie().getId()).isEqualTo(1);
        assertThat(session.getStart())
                .isEqualTo(LocalDateTime.of(2023,1,8,9,5, 0));
        UserRepository userRepository = new JdbcUserRepository(dataSource);
        User user1 = userRepository.findById(1).orElse(null);
        assertThat(user1).isNotNull();
        TicketRepository store = new JdbcTicketRepository(dataSource);
        List<Ticket> tickets = store.findAllBySession(session.getId());
        int count = tickets.size();
        Ticket ticket1 = new Ticket(1, session, 1, 1, user1);
        ticket1 = store.add(ticket1).orElse(null);
        assertThat(ticket1).isNotNull();
        tickets = store.findAllBySession(session.getId());
        assertThat(tickets.size()).isEqualTo(count+1);
        Ticket ticketInDb1 = tickets.get(count);
        assertThat(ticketInDb1).isNotNull();
        assertThat(ticketInDb1.getId()).isEqualTo(ticket1.getId());
        assertThat(ticketInDb1.getSession()).isEqualTo(ticket1.getSession());
        assertThat(ticketInDb1.getRow()).isEqualTo(ticket1.getRow());
        assertThat(ticketInDb1.getCell()).isEqualTo(ticket1.getCell());
        assertThat(ticketInDb1.getUser()).isEqualTo(ticket1.getUser());

        Ticket ticket2 = new Ticket(1, session, 1, 2, user1);
        ticket2 = store.add(ticket2).orElse(null);
        assertThat(ticket2).isNotNull();
        tickets = store.findAllBySession(session.getId());
        assertThat(tickets.size()).isEqualTo(count+2);
        Ticket ticketInDb2 = tickets.get(count+1);
        assertThat(ticketInDb2).isNotNull();
        assertThat(ticketInDb2.getId()).isEqualTo(ticket2.getId());
        assertThat(ticketInDb2.getSession()).isEqualTo(ticket2.getSession());
        assertThat(ticketInDb2.getRow()).isEqualTo(ticket2.getRow());
        assertThat(ticketInDb2.getCell()).isEqualTo(ticket2.getCell());
        assertThat(ticketInDb2.getUser()).isEqualTo(ticket2.getUser());

        ticketInDb1 = tickets.get(count);
        assertThat(ticketInDb1).isNotNull();
        assertThat(ticketInDb1.getId()).isEqualTo(ticket1.getId());
        assertThat(ticketInDb1.getSession()).isEqualTo(ticket1.getSession());
        assertThat(ticketInDb1.getRow()).isEqualTo(ticket1.getRow());
        assertThat(ticketInDb1.getCell()).isEqualTo(ticket1.getCell());
        assertThat(ticketInDb1.getUser()).isEqualTo(ticket1.getUser());
    }

    @Test
    public void whenCreate2TicketAndFindAllByUser() {
        SessionRepository sessionRepository = new JdbcSessionRepository(dataSource);
        Session session = sessionRepository.findById(1).orElse(null);
        assertThat(session).isNotNull();
        assertThat(session.getId()).isEqualTo(1);
        assertThat(session.getRoom().getId()).isEqualTo(1);
        assertThat(session.getMovie().getId()).isEqualTo(1);
        assertThat(session.getStart())
                .isEqualTo(LocalDateTime.of(2023,1,8,9,5, 0));
        UserRepository userRepository = new JdbcUserRepository(dataSource);
        User user1 = userRepository.findById(1).orElse(null);
        assertThat(user1).isNotNull();
        TicketRepository store = new JdbcTicketRepository(dataSource);
        List<Ticket> tickets = store.findAllByUser(user1.getId());
        int count = tickets.size();
        Ticket ticket1 = new Ticket(1, session, 1, 1, user1);
        ticket1 = store.add(ticket1).orElse(null);
        assertThat(ticket1).isNotNull();

        tickets = store.findAllByUser(user1.getId());
        assertThat(tickets.size()).isEqualTo(count+1);

        Ticket ticketInDb1 = tickets.get(count);
        assertThat(ticketInDb1).isNotNull();
        assertThat(ticketInDb1.getId()).isEqualTo(ticket1.getId());
        assertThat(ticketInDb1.getSession()).isEqualTo(ticket1.getSession());
        assertThat(ticketInDb1.getRow()).isEqualTo(ticket1.getRow());
        assertThat(ticketInDb1.getCell()).isEqualTo(ticket1.getCell());
        assertThat(ticketInDb1.getUser()).isEqualTo(ticket1.getUser());

        Ticket ticket2 = new Ticket(1, session, 1, 2, user1);
        ticket2 = store.add(ticket2).orElse(null);
        assertThat(ticket2).isNotNull();

        tickets = store.findAllByUser(user1.getId());
        assertThat(tickets.size()).isEqualTo(count+2);

        Ticket ticketInDb2 = tickets.get(count+1);
        assertThat(ticketInDb2).isNotNull();
        assertThat(ticketInDb2.getId()).isEqualTo(ticket2.getId());
        assertThat(ticketInDb2.getSession()).isEqualTo(ticket2.getSession());
        assertThat(ticketInDb2.getRow()).isEqualTo(ticket2.getRow());
        assertThat(ticketInDb2.getCell()).isEqualTo(ticket2.getCell());
        assertThat(ticketInDb2.getUser()).isEqualTo(ticket2.getUser());

        ticketInDb1 = tickets.get(count);
        assertThat(ticketInDb1).isNotNull();
        assertThat(ticketInDb1.getId()).isEqualTo(ticket1.getId());
        assertThat(ticketInDb1.getSession()).isEqualTo(ticket1.getSession());
        assertThat(ticketInDb1.getRow()).isEqualTo(ticket1.getRow());
        assertThat(ticketInDb1.getCell()).isEqualTo(ticket1.getCell());
        assertThat(ticketInDb1.getUser()).isEqualTo(ticket1.getUser());
    }

    @Test
    public void whenCreate2TicketAndFindAllBySessionAndUser() {
        SessionRepository sessionRepository = new JdbcSessionRepository(dataSource);
        Session session = sessionRepository.findById(1).orElse(null);
        assertThat(session).isNotNull();
        assertThat(session.getId()).isEqualTo(1);
        assertThat(session.getRoom().getId()).isEqualTo(1);
        assertThat(session.getMovie().getId()).isEqualTo(1);
        assertThat(session.getStart())
                .isEqualTo(LocalDateTime.of(2023,1,8,9,5, 0));
        UserRepository userRepository = new JdbcUserRepository(dataSource);
        User user1 = userRepository.findById(1).orElse(null);
        assertThat(user1).isNotNull();
        TicketRepository store = new JdbcTicketRepository(dataSource);
        List<Ticket> tickets = store.findAllBySessionAndUser(session.getId(), user1.getId());
        int count = tickets.size();
        Ticket ticket1 = new Ticket(1, session, 1, 1, user1);
        ticket1 = store.add(ticket1).orElse(null);
        assertThat(ticket1).isNotNull();

        tickets = store.findAllBySessionAndUser(session.getId(), user1.getId());
        assertThat(tickets.size()).isEqualTo(count+1);

        Ticket ticketInDb1 = tickets.get(count);
        assertThat(ticketInDb1).isNotNull();
        assertThat(ticketInDb1.getId()).isEqualTo(ticket1.getId());
        assertThat(ticketInDb1.getSession()).isEqualTo(ticket1.getSession());
        assertThat(ticketInDb1.getRow()).isEqualTo(ticket1.getRow());
        assertThat(ticketInDb1.getCell()).isEqualTo(ticket1.getCell());
        assertThat(ticketInDb1.getUser()).isEqualTo(ticket1.getUser());

        Ticket ticket2 = new Ticket(1, session, 1, 2, user1);
        ticket2 = store.add(ticket2).orElse(null);
        assertThat(ticket2).isNotNull();

        tickets = store.findAllBySessionAndUser(session.getId(), user1.getId());
        assertThat(tickets.size()).isEqualTo(count+2);

        Ticket ticketInDb2 = tickets.get(count+1);
        assertThat(ticketInDb2).isNotNull();
        assertThat(ticketInDb2.getId()).isEqualTo(ticket2.getId());
        assertThat(ticketInDb2.getSession()).isEqualTo(ticket2.getSession());
        assertThat(ticketInDb2.getRow()).isEqualTo(ticket2.getRow());
        assertThat(ticketInDb2.getCell()).isEqualTo(ticket2.getCell());
        assertThat(ticketInDb2.getUser()).isEqualTo(ticket2.getUser());

        ticketInDb1 = tickets.get(count);
        assertThat(ticketInDb1).isNotNull();
        assertThat(ticketInDb1.getId()).isEqualTo(ticket1.getId());
        assertThat(ticketInDb1.getSession()).isEqualTo(ticket1.getSession());
        assertThat(ticketInDb1.getRow()).isEqualTo(ticket1.getRow());
        assertThat(ticketInDb1.getCell()).isEqualTo(ticket1.getCell());
        assertThat(ticketInDb1.getUser()).isEqualTo(ticket1.getUser());
    }
}