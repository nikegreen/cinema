package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Seat;

import java.util.List;

public interface SeatRepository {
    List<List<Seat>> getByRoomId(int roomId);

    /**
     * @param roomId - in interval from database 'cinema' table 'rooms'
     * @param rowIndex - in interval 1...maxRowIndex
     * @return row - included empty (==NULL) seats. order by id from table 'seats'.
     */
    List<Seat> getByRoomIdAndRow(int roomId, int rowIndex);
}
