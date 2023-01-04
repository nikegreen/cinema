package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.service.RoomService;
import ru.job4j.cinema.service.SeatService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.util.ModelSet;
import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class SessionController {
    private final SessionService sessionService;
    private final RoomService roomService;
    private final TicketService ticketService;
    private final SeatService seatService;

    public SessionController(SessionService sessionService,
                             RoomService roomService,
                             TicketService ticketService,
                             SeatService seatService) {
        this.sessionService = sessionService;
        this.roomService = roomService;
        this.ticketService = ticketService;
        this.seatService = seatService;
    }

    @GetMapping("/formRow/{sessionId}")
    public String formRow(
            Model model, HttpSession httpSession, @PathVariable("sessionId") int id
    ) {
        model = ModelSet.fromSession(model, httpSession);
        Session cinemaSession = this.sessionService.findById(id).orElse(null);
        model.addAttribute("cinemasession", cinemaSession);
        model.addAttribute("seats",
                roomService.getSeats(
                        cinemaSession.getId(),
                        cinemaSession.getRoom().getId(),
                        seatService,
                        ticketService
                )
        );
        model.addAttribute("row", 1);
        return "row";
    }

    @GetMapping("/formCell/{rowIndex}")
    public String formCell(
            Model model,
            HttpSession httpSession,
            @PathVariable("rowIndex") int rowIndex,
            @RequestParam("session_id") int sessionId
    ) {
        model = ModelSet.fromSession(model, httpSession);
        Session cinemaSession = this.sessionService.findById(sessionId).orElse(null);
        model.addAttribute("cinemasession", cinemaSession);
        model.addAttribute("seats",
                roomService.getSeats(
                        cinemaSession.getId(),
                        cinemaSession.getRoom().getId(),
                        seatService,
                        ticketService
                )
        );
        model.addAttribute("row_index", rowIndex);
        return "cell";
    }
}
