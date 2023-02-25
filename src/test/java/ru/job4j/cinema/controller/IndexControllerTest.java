package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.model.Room;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

/**
 * Проверка функций контроллера IndexControllerTest
 * @author nikez
 * @version $Id: $Id
 */
class IndexControllerTest {
    /**
     * Проверка запроса get страница /index
     */
    @Test
    public void whenIndex() {
        Room room1 = new Room(1, "litle");
        Room room2 = new Room(2, "midle");
        Room room3 = new Room(3, "big");
        List<Session> cinemaSessions = Arrays.asList(
                new Session(
                        1,
                        "",
                        new Movie(1, "film1", "images1.jpg"),
                        room1,
                        LocalDateTime.of(2023, 1, 10, 12, 10, 0)
                ),
                new Session(
                        2,
                        "",
                        new Movie(2, "film2", "images2.jpg"),
                        room2,
                        LocalDateTime.of(2023, 1, 10, 12, 10, 0)
                ),
                new Session(
                        3,
                        "",
                        new Movie(3, "film3", "images3.jpg"),
                        room3,
                        LocalDateTime.of(2023, 1, 10, 12, 10, 0)
                ),
                new Session(
                        4,
                        "",
                        new Movie(4, "film4", "images4.jpg"),
                        room1,
                        LocalDateTime.of(2023, 1, 10, 12, 10, 0)
                ),
                new Session(
                        5,
                        "",
                        new Movie(5, "film5", "images5.jpg"),
                        room2,
                        LocalDateTime.of(2023, 1, 10, 12, 10, 0)
                ),
                new Session(
                        6,
                        "",
                        new Movie(1, "film6", "images6.jpg"),
                        room3,
                        LocalDateTime.of(2023, 1, 10, 12, 10, 0)
                )
        );
        SessionService sessionService = mock(SessionService.class);
        when(sessionService.findAll()).thenReturn(cinemaSessions);

        Model model = mock(Model.class);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(null);

        IndexController indexController = new IndexController(sessionService);

        String page = indexController.index(model, session);

        User userGuest = new User();
        userGuest.setUsername("Гость");

        verify(model).addAttribute("cinemasessions", cinemaSessions);
        verify(model).addAttribute("user", userGuest);
        assertThat(page).isEqualTo("index");
    }
}