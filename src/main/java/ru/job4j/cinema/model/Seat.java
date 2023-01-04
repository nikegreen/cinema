package ru.job4j.cinema.model;

import java.io.Serializable;
import java.util.Objects;

public class Seat implements Serializable {
    private int id;
    private int roomId;
    private int row;
    private int cell;

    private boolean empty;

    public Seat() {

    }

    public Seat(int id, int roomId, int row, int cell, boolean empty) {
        this.id = id;
        this.roomId = roomId;
        this.row = row;
        this.cell = cell;
        this.empty = empty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seat seat = (Seat) o;
        return id == seat.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Seat{"
                + "id=" + id
                + ", roomId=" + roomId
                + ", row=" + row
                + ", cell=" + cell
                + ", empty=" + empty
                + '}';
    }
}
