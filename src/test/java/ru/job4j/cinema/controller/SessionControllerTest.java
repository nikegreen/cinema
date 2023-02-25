package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.*;
import ru.job4j.cinema.service.RoomService;
import ru.job4j.cinema.service.SeatService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

/**
 * Проверка функций контроллера SessionControllerTest
 * @author nikez
 * @version $Id: $Id
 */
class SessionControllerTest {
    /**
     * Проверка запроса get страница /formRow/
     */
    @Test
    public void whenFormRow() {
        List<List<Seat>> seats = Arrays.asList(
                Arrays.asList(
                        new Seat(1, 1, 1, 1, true),
                        new Seat(2, 1, 1, 2, true),
                        new Seat(3, 1, 1, 3, true)
                ),
                Arrays.asList(
                        new Seat(4, 1, 2, 1, true),
                        new Seat(5, 1, 2, 2, true),
                        new Seat(6, 1, 2, 3, true)
                )
        );
        List<Ticket> tickets = Arrays.asList();
        Room room1 = new Room(1, "little");
        Movie movie1 = new Movie(1, "film1", "images1.jpg");
        Session cinemaSession = new Session(
                1,
                "",
                movie1,
                room1,
                LocalDateTime.of(2023, 1, 10, 12, 10, 0)
        );
        SessionService sessionService = mock(SessionService.class);
        when(sessionService.findById(1)).thenReturn(Optional.of(cinemaSession));
        TicketService ticketService = mock(TicketService.class);
        when(ticketService.findAllBySession(1)).thenReturn(tickets);
        SeatService seatService = mock(SeatService.class);
        when(seatService.getByRoomId(1)).thenReturn(seats);
        RoomService roomService = mock(RoomService.class);
        when(roomService.findById(1)).thenReturn(Optional.of(room1));
        when(roomService.getSeats(1, 1, seatService, ticketService)).thenReturn(seats);
        SessionController sessionController = new SessionController(
                sessionService, roomService, ticketService, seatService
        );
        Model model = mock(Model.class);
        User user = new User(
                1,
                "admin",
                "admin",
                "admin@email.ru",
                "+79999999999"
        );
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);
        String page = sessionController.formRow(model, session, 1);
        verify(session).setAttribute("cinemasession", cinemaSession);
        verify(model).addAttribute("seats", seats);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo("row");
    }

    /**
     * Проверка запроса get страница /formСell/
     */
    @Test
    public void whenFormCell() {
        int rowIndex = 1;
        List<List<Seat>> seats = Arrays.asList(
                Arrays.asList(
                        new Seat(1, 1, 1, 1, true),
                        new Seat(2, 1, 1, 2, true),
                        new Seat(3, 1, 1, 3, true)
                ),
                Arrays.asList(
                        new Seat(4, 1, 2, 1, true),
                        new Seat(5, 1, 2, 2, true),
                        new Seat(6, 1, 2, 3, true)
                )
        );
        List<Ticket> tickets = Arrays.asList();
        Room room1 = new Room(1, "little");
        Movie movie1 = new Movie(1, "film1", "images1.jpg");
        Session cinemaSession = new Session(
                1,
                "",
                movie1,
                room1,
                LocalDateTime.of(2023, 1, 10, 12, 10, 0)
        );
        SessionService sessionService = mock(SessionService.class);
        when(sessionService.findById(1)).thenReturn(Optional.of(cinemaSession));
        TicketService ticketService = mock(TicketService.class);
        when(ticketService.findAllBySession(1)).thenReturn(tickets);
        SeatService seatService = mock(SeatService.class);
        when(seatService.getByRoomId(1)).thenReturn(seats);
        RoomService roomService = mock(RoomService.class);
        when(roomService.findById(1)).thenReturn(Optional.of(room1));
        when(roomService.getSeats(1, 1, seatService, ticketService)).thenReturn(seats);
        SessionController sessionController = new SessionController(
                sessionService, roomService, ticketService, seatService
        );
        Model model = mock(Model.class);
        User user = new User(
                1,
                "admin",
                "admin",
                "admin@email.ru",
                "+79999999999"
        );
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("cinemasession")).thenReturn(cinemaSession);
        String page = sessionController.formCell(model, session, rowIndex);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("seats", seats);
        verify(session).setAttribute("row_index", rowIndex);
        assertThat(page).isEqualTo("cell");
    }
}