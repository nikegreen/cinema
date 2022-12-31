package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Room;
import ru.job4j.cinema.repository.JdbcRoomRepository;
import ru.job4j.cinema.repository.RoomRepository;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class RoomService {
    private final RoomRepository store;

    public RoomService(JdbcRoomRepository store) {
        this.store = store;
    }

    public List<Room> findAll() {
        return store.findAll();
    }

    public Optional<Room> add(Room room) {
        return store.add(room);
    }

    public Optional<Room> findById(int id) {
        return store.findById(id);
    }
}
