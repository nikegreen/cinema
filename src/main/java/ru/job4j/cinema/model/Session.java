package ru.job4j.cinema.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Session {
    private int id;
    private String name;
    private Movie movie;
    private Room room;
    private LocalDateTime start;

    public Session() {

    }

    public Session(int id,
                   String name,
                   Movie movie,
                   Room room,
                   LocalDateTime start) {
        this.id = id;
        this.name = name;
        this.movie = movie;
        this.room = room;
        this.start = start;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return id == session.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Session{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", movie=" + movie
                + ", room=" + room
                + ", start=" + start
                + '}';
    }
}
