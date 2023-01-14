package ru.job4j.cinema.model;

import java.util.Objects;

public class Ticket {
    private int id;
    private Session session;
    private int row;
    private int cell;
    private User user;

    public Ticket() {

    }

    public Ticket(int id,
                  Session session,
                  int row,
                  int cell,
                  User user) {
        this.id = id;
        this.session = session;
        this.row = row;
        this.cell = cell;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ticket{"
                + "id=" + id
                + ", session=" + session
                + ", row=" + row
                + ", cell=" + cell
                + ", user=" + user
                + '}';
    }
}
