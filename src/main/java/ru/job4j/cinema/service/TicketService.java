package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.JdbcTicketRepository;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.List;
import java.util.Optional;

/**
 * <p>TicketService class. Сервис для Билетов</p>
 * @author nikez
 * @version $Id: $Id
 */
@ThreadSafe
@Service
public class TicketService {
    private final TicketRepository store;
    private final UserService userService;
    private final SessionService sessionService;

    public TicketService(JdbcTicketRepository store,
                         UserService userService,
                         SessionService sessionService) {
        this.store = store;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    /**
     * Список билетов купленных на киносеанс
     * @param sessionId идентификатор киносеана
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Ticket>} список всех Билетов
     */
    public List<Ticket> findAllBySession(int sessionId) {
        Session session = sessionService.findById(sessionId).orElse(null);
        List<Ticket> list = store.findAllBySession(sessionId);
        list.forEach(ticket -> {
            ticket.setSession(session);
            ticket.setUser(userService.findById(ticket.getUser().getId()).orElse(null));
        });
        return list;
    }

    /**
     * Все билеты купленные пользователем
     * @param userId - идентификатор пользователя
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Ticket>} список всех Билетов
     */
    public List<Ticket> findAllByUser(int userId) {
        User user = userService.findById(userId).orElse(null);
        List<Ticket> list = store.findAllByUser(userId);
        list.forEach(ticket -> {
            ticket.setSession(sessionService.findById(ticket.getSession().getId()).orElse(null));
            ticket.setUser(user);
        });
        return list;
    }

    /**
     * Все билеты купленные пользователем на выбранный киносеанс
     * @param sessionId - идентификатор Киносеанса
     * @param userId - идентификатор пользователя
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Ticket>} список всех Билетов
     */
    public List<Ticket> findAllBySessionAndUser(int sessionId, int userId) {
        Session session = sessionService.findById(sessionId).orElse(null);
        User user = userService.findById(userId).orElse(null);
        List<Ticket> list = store.findAllBySessionAndUser(sessionId, userId);
        list.forEach(ticket -> {
            ticket.setSession(session);
            ticket.setUser(user);
        });
        return list;
    }

    /**
     * Добавить Билет в хранилище
     * @param ticket тип {@link ru.job4j.cinema.model.Room} добавляемый Билет
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Ticket>} результат добавления:
     * Optional.Empty - Билет не добавлен иначе
     * Optional<Ticket> Билет с новым идентификатором.
     */
    public Optional<Ticket> add(Ticket ticket) {
        return store.add(ticket);
    }

    /**
     * Поиск Билета по идентификатору
     * @param id - идентификатор Билета
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Ticket>} результат поиска
     * Optional.Empty - Билет не найден иначе
     * Optional<Ticket> найденный Билет.
     */
    public Optional<Ticket> findById(int id) {
        Ticket ticket = store.findById(id).orElse(null);
        ticket.setSession(sessionService.findById(ticket.getSession().getId()).orElse(null));
        ticket.setUser(userService.findById(ticket.getUser().getId()).orElse(null));
        return Optional.ofNullable(ticket);
    }
}
