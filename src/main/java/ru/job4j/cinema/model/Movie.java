package ru.job4j.cinema.model;

import java.util.Objects;

public class Movie {
    private int id;
    private String name;
    private String filename;

    public Movie() {

    }

    public Movie(int id, String name, String filename) {
        this.id = id;
        this.name = name;
        this.filename = filename;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return id == movie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Movie{id=" + id
                + ", name='" + name + '\''
                + ", filename='" + filename + '\''
                + '}';
    }
}
