package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Room;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.JdbcRoomRepository;
import ru.job4j.cinema.repository.RoomRepository;
import java.util.List;
import java.util.Optional;

/**
 * <p>RoomService class. Сервис для Кинозалов</p>
 * @author nikez
 * @version $Id: $Id
 */
@ThreadSafe
@Service
public class RoomService {
    private final RoomRepository store;

    /**
     * Конструктор сервиса
     * @param store - хранилище кинзалов
     */
    public RoomService(JdbcRoomRepository store) {
        this.store = store;
    }

    /**
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Room>} список всех Кинозалов
     */
    public List<Room> findAll() {
        return store.findAll();
    }

    /**
     * Добавить Кинозал в хранилище
     * @param room тип {@link ru.job4j.cinema.model.Room} добавляемый Кинозал
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Room>} результат добавления:
     * Optional.Empty - не добавлен иначе
     * Optional<Movie> Кинозал с новым идентификатором Кинозала.
     */
    public Optional<Room> add(Room room) {
        return store.add(room);
    }

    /**
     * Поиск Кинозала по идентификатору
     * @param id - идентификатор Кинозала
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Room>} результат поиска
     * Optional.Empty - не найден иначе
     * Optional<Movie> найденный Кинозал.
     */
    public Optional<Room> findById(int id) {
        return store.findById(id);
    }

    /**
     * Получить места в кинозале с учетом проданных билетов
     * @param sessionId - идентификатор киносеанса
     * @param roomId - идентификатор кинозала
     * @param seatService - сервис мест в кинозале
     * @param ticketService - сервис проданных билетов
     * @return тип {@link java.util.List<java.util.List<ru.job4j.cinema.model.Seat>>}
     * список мест с заполнением признака empty
     *  empty == true  - свободно,
     *  empty == false - занято.
     */
    public List<List<Seat>> getSeats(
            int sessionId,
            int roomId,
            SeatService seatService,
            TicketService ticketService) {
        List<List<Seat>> result = seatService.getByRoomId(roomId);
        List<Ticket> tickets = ticketService.findAllBySession(sessionId);
        result.forEach(row -> row.forEach(seat -> seat.setEmpty(true)));
        for (Ticket ticket: tickets) {
            int row = ticket.getRow();
            if (row > 0) {
                result.get(row - 1).forEach(
                        seat -> {
                            if (seat.getCell() == ticket.getCell()) {
                                seat.setEmpty(false);
                            }
                        }
                );
            }
        }
        return result;
    }
}
