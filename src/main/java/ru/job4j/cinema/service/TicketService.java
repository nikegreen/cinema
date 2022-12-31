package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.JdbcTicketRepository;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.List;
import java.util.Optional;

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

    List<Ticket> findAllBySession(int sessionId) {
        List<Ticket> list = store.findAllBySession(sessionId);
        list.forEach(ticket -> {
            ticket.setSession(sessionService.findById(ticket.getSession().getId()).orElse(null));
            ticket.setUser(userService.findById(ticket.getUser().getId()).orElse(null));
        });
        return list;
    }

    Optional<Ticket> add(Ticket ticket) {
        return store.add(ticket);
    }

    Optional<Ticket> findById(int id) {
        Ticket ticket = store.findById(id).orElse(null);
        ticket.setSession(sessionService.findById(ticket.getSession().getId()).orElse(null));
        ticket.setUser(userService.findById(ticket.getUser().getId()).orElse(null));
        return Optional.ofNullable(ticket);
    }

}
