package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Seat;

import java.util.List;

/**
 * <p>SeatRepository class. Репозиторий для Сидений (интерфейс хранилища)</p>
 * @author nikez
 * @version $Id: $Id
 */
public interface SeatRepository {
    /**
     * @param roomId - in interval from database 'cinema' table 'rooms'
     * @return collection result[row][cell] - included empty (==NULL) seats.
     * order by id from table 'seats'.
     * --
     * example of use:
     * List<List<Seat>> seats = this.getByRoomId(int roomId);
     * int row_index = 0;
     * int cell_index = 0;
     * Seat seat = seats.get(row_index).get(cell_index);
     */
    List<List<Seat>> getByRoomId(int roomId);

    /**
     * @param roomId - in interval from database 'cinema' table 'rooms'
     * @param rowIndex - in interval 1...maxRowIndex
     * @return row - included empty (==NULL) seats. order by id from table 'seats'.
     * Result: included collection objects of Seat class have rowIndex.
     */
    List<Seat> getByRoomIdAndRow(int roomId, int rowIndex);
}
