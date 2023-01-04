package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.repository.JdbcSeatRepository;
import ru.job4j.cinema.repository.SeatRepository;

import java.util.List;

@ThreadSafe
@Service
public class SeatService {
    private final SeatRepository store;

    public SeatService(JdbcSeatRepository store) {
        this.store = store;
    }

    public List<List<Seat>> getByRoomId(int roomId) {
        return store.getByRoomId(roomId);
    }

    public List<Seat> getByRoomIdAndRow(int roomId, int rowIndex) {
        return store.getByRoomIdAndRow(roomId, rowIndex);
    }
}
