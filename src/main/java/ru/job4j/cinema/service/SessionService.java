package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.JdbcSessionRepository;
import ru.job4j.cinema.repository.SessionRepository;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class SessionService {
    private final SessionRepository store;
    private final MovieService movieService;
    private final RoomService roomService;

    public SessionService(JdbcSessionRepository store,
                          MovieService movieService,
                          RoomService roomService) {
        this.store = store;
        this.movieService = movieService;
        this.roomService = roomService;
    }

    public List<Session> findAll() {
        List<Session> list = store.findAll();
            list.forEach(session -> {
                session.setMovie(movieService.findById(session.getMovie().getId()).orElse(null));
                session.setRoom(roomService.findById(session.getRoom().getId()).orElse(null));
        });
        return list;
    }

    public Optional<Session> add(Session session) {
        return store.add(session);
    }

    public Optional<Session> findById(int id) {
        Session session = store.findById(id).orElse(null);
        session.setMovie(movieService.findById(session.getMovie().getId()).orElse(null));
        session.setRoom(roomService.findById(session.getRoom().getId()).orElse(null));
        return Optional.ofNullable(session);
    }
}
