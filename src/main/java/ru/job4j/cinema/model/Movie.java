package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * <p>Movie class. Модель данных для Фильма</p>
 * @author nikez
 * @version $Id: $Id
 */
public class Movie {
    private int id;
    private String name;
    private String filename;

    public Movie() {

    }

    /**
     * Конструктор Фильма
     * @param id - идентификатор
     * @param name - название фильма
     * @param filename - название картинки с афишей
     */
    public Movie(int id, String name, String filename) {
        this.id = id;
        this.name = name;
        this.filename = filename;
    }

    /**
     * Геттер для идентификатора
     * @return идентификатор
     */
    public int getId() {
        return id;
    }

    /**
     * Сеттер идентификатора
     * @param id - новый идентификато
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * геттер названия фильма
     * @return - тип {@link java.lang.String} содержит название фильма
     */
    public String getName() {
        return name;
    }

    /**
     * Сеттер названия фильма
     * @param name - тип {@link java.lang.String} содержит новое название фильма
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Геттер имя файла с картинкой
     * @return - тип {@link java.lang.String} содержит имя файла с картинкой
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Сеттер имени файла с картинкой
     * @param filename - тип {@link java.lang.String} содержит новое имя файла с картинкой
     */
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
