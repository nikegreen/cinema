package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.JdbcSessionRepository;
import ru.job4j.cinema.repository.SessionRepository;
import java.util.List;
import java.util.Optional;

/**
 * <p>SessionService class. Сервис для Киносеансов</p>
 * @author nikez
 * @version $Id: $Id
 */
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

    /**
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Session>} список всех Киносеансов
     */
    public List<Session> findAll() {
        List<Session> list = store.findAll();
            list.forEach(session -> {
                session.setMovie(movieService.findById(session.getMovie().getId()).orElse(null));
                session.setRoom(roomService.findById(session.getRoom().getId()).orElse(null));
        });
        return list;
    }

    /**
     * Добавить Киносеанс в хранилище
     * @param session тип {@link ru.job4j.cinema.model.Session} добавляемый Киносеанс
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Session>} результат добавления:
     * Optional.Empty - не добавлен иначе
     * Optional<Movie> Киносеанс с новым идентификатором Киносеанса.
     */
    public Optional<Session> add(Session session) {
        return store.add(session);
    }

    /**
     * Поиск Киносеанса по идентификатору
     * @param id - идентификатор Киносеанса
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Session>} результат поиска
     * Optional.Empty - Киносеанс не найден иначе
     * Optional<Movie> найденный Киносеанс.
     */
    public Optional<Session> findById(int id) {
        Session session = store.findById(id).orElse(null);
        session.setMovie(movieService.findById(session.getMovie().getId()).orElse(null));
        session.setRoom(roomService.findById(session.getRoom().getId()).orElse(null));
        return Optional.ofNullable(session);
    }
}
