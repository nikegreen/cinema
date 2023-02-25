package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.repository.JdbcSeatRepository;
import ru.job4j.cinema.repository.SeatRepository;

import java.util.List;

/**
 * <p>SeatService class. Сервис для Сидений (места в кинозале)</p>
 * @author nikez
 * @version $Id: $Id
 */
@ThreadSafe
@Service
public class SeatService {
    private final SeatRepository store;

    /**
     * Конструктор
     * @param store тип {@link ru.job4j.cinema.repository.JdbcSeatRepository}
     */
    public SeatService(JdbcSeatRepository store) {
        this.store = store;
    }

    /**
     * Поиск списка сидений по идентификатору Кинозала
     * @param roomId - идентификатор Кинозала
     * @return тип {@link java.util.List}<{@link  java.util.List<ru.job4j.cinema.model.Seat>}>
     * список мест в кинозале
     */
    public List<List<Seat>> getByRoomId(int roomId) {
        return store.getByRoomId(roomId);
    }

    /**
     * Поиск списка сидений по идентификатору Кинозала
     * @param roomId - идентификатор Кинозала
     * @param rowIndex - номер ряда в Кинозале
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Seat>}
     * список мест в ряду для кинозала
     */
    public List<Seat> getByRoomIdAndRow(int roomId, int rowIndex) {
        return store.getByRoomIdAndRow(roomId, rowIndex);
    }
}
