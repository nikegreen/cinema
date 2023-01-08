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

class TicketControllerTest {
    @Test
    public void whenFormBuy() {
        int rowIndex = 1;
        int cellIndex = 1;
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
        TicketController ticketController = new TicketController(ticketService, roomService, seatService);
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
        when(session.getAttribute("row_index")).thenReturn(rowIndex);
        String page = ticketController.formBuy(model, session, 1);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("seats", seats);
        verify(session).setAttribute("cell_index", cellIndex);
        assertThat(page).isEqualTo("buy");
    }

    @Test
    public void whenCreateTicket() {
        int rowIndex = 1;
        int cellIndex = 1;
        User user = new User(
                1,
                "admin",
                "admin",
                "admin@email.ru",
                "+79999999999"
        );
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
        Ticket ticket = new Ticket(
                0,
                cinemaSession,
                rowIndex,
                cellIndex,
                user
        );
        Ticket ticketRes = new Ticket(
                1,
                cinemaSession,
                rowIndex,
                cellIndex,
                user
        );
        TicketService ticketService = mock(TicketService.class);
        when(ticketService.findAllBySession(1)).thenReturn(tickets);
        when(ticketService.add(ticket)).thenReturn(Optional.of(ticketRes));
        SeatService seatService = mock(SeatService.class);
        when(seatService.getByRoomId(1)).thenReturn(seats);
        RoomService roomService = mock(RoomService.class);
        when(roomService.findById(1)).thenReturn(Optional.of(room1));
        when(roomService.getSeats(1, 1, seatService, ticketService)).thenReturn(seats);
        TicketController ticketController = new TicketController(ticketService, roomService, seatService);
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("cinemasession")).thenReturn(cinemaSession);
        when(session.getAttribute("row_index")).thenReturn(rowIndex);
        when(session.getAttribute("cell_index")).thenReturn(cellIndex);
        String page = ticketController.createTicket(session);
        verify(session).setAttribute("session_filtr", cinemaSession.getId());
        assertThat(page).isEqualTo("redirect:/formTickets");
    }

    @Test
    public void whenFormTickets() {
        int rowIndex = 1;
        int cellIndex = 1;
        User user = new User(
                1,
                "admin",
                "admin",
                "admin@email.ru",
                "+79999999999"
        );
        Room room1 = new Room(1, "little");
        Movie movie1 = new Movie(1, "film1", "images1.jpg");
        Session cinemaSession = new Session(
                1,
                "",
                movie1,
                room1,
                LocalDateTime.of(2023, 1, 10, 12, 10, 0)
        );

        Ticket ticket = new Ticket(
                1,
                cinemaSession,
                rowIndex,
                cellIndex,
                user
        );
        List<Ticket> tickets = Arrays.asList(ticket);
        TicketService ticketService = mock(TicketService.class);
        when(ticketService.findAllBySessionAndUser(
                cinemaSession.getId(), user.getId())).thenReturn(tickets);
        when(ticketService.findAllBySession(cinemaSession.getId())).thenReturn(tickets);

        SeatService seatService = mock(SeatService.class);

        RoomService roomService = mock(RoomService.class);

        TicketController ticketController = new TicketController(ticketService, roomService, seatService);

        Model model = mock(Model.class);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("cinemasession")).thenReturn(cinemaSession);
        when(session.getAttribute("session_filtr")).thenReturn(cinemaSession.getId());

        String page = ticketController.formTickets(model, session);

        verify(model).addAttribute("tickets", tickets);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo("tickets");
    }

    @Test
    public void whenBuyFail() {
        User user = new User(
                1,
                "admin",
                "admin",
                "admin@email.ru",
                "+79999999999"
        );
        TicketService ticketService = mock(TicketService.class);
        SeatService seatService = mock(SeatService.class);
        RoomService roomService = mock(RoomService.class);
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);
        TicketController ticketController = new TicketController(ticketService, roomService, seatService);
        String page = ticketController.formBuyFail(model, session);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo("buyFail");
    }
}