package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    List<Room> findAll();

    Optional<Room> add(Room room);

    Optional<Room> findById(int id);
}
