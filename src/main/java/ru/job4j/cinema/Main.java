package ru.job4j.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Main class. Sping boot application</p>
 * 1. run application.
 * 2. in web browser open page 'http://localhost:8080/index'.
 * @author nike z
 * @version $Id: $Id
 */
@SpringBootApplication
public class Main {
    /**
     * <p>main.</p>
     *
     * @param args an array of {@link String} objects.
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Go to http://localhost:8080/index");
    }
}
