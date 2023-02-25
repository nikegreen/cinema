package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.service.RoomService;
import ru.job4j.cinema.service.SeatService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.util.ModelSet;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>SessionController class.</p>
 * @author nikez
 * @version $Id: $Id
 */
@ThreadSafe
@Controller
public class SessionController {
    private final SessionService sessionService;
    private final RoomService roomService;
    private final TicketService ticketService;
    private final SeatService seatService;

    /**
     * Конструктор
     * @param sessionService - сервис Киносеансов
     * @param roomService - сервис кинозалов
     * @param ticketService - сервис билетов
     * @param seatService - севис сидений/мест
     */
    public SessionController(SessionService sessionService,
                             RoomService roomService,
                             TicketService ticketService,
                             SeatService seatService) {
        this.sessionService = sessionService;
        this.roomService = roomService;
        this.ticketService = ticketService;
        this.seatService = seatService;
    }

    /**
     * <p>Страница выбора ряда в зале</p>
     * Выводит страницу с киносеансом, показывается зал для выбора ряда
     * @param model - объект передаёт данные в страницу
     * @param httpSession - объект http сессии
     * @param sessionId - ID киносеанса
     * @return - тип {@link java.lang.String} содержит:
     *  "row" - OK
     *  "redirect:/index" - error
     */
    @GetMapping("/formRow/{sessionId}")
    public String formRow(
            Model model, HttpSession httpSession, @PathVariable("sessionId") int sessionId
    ) {
        model = ModelSet.fromSession(model, httpSession);
        Session cinemaSession = this.sessionService.findById(sessionId).orElse(null);
        if (cinemaSession == null) {
            return "redirect:/index";
        }
        httpSession.setAttribute("cinemasession", cinemaSession);
        List<List<Seat>> seats = roomService.getSeats(
                cinemaSession.getId(),
                cinemaSession.getRoom().getId(),
                seatService,
                ticketService
        );
        model.addAttribute("seats", seats);
        return "row";
    }

    /**
     * <p>Страница выбора места в ряду</p>
     * @param model - объект передаёт данные в страницу
     * @param httpSession - объект http сессии
     * @param rowIndex - индекс ряда в кинозале
     * @return - тип {@link java.lang.String} содержит:
     *  "cell" - OK
     *  "row" - error
     */
    @GetMapping("/formCell/{rowIndex}")
    public String formCell(
            Model model,
            HttpSession httpSession,
            @PathVariable("rowIndex") int rowIndex
    ) {
        model = ModelSet.fromSession(model, httpSession);
        Session cinemaSession = (Session) httpSession.getAttribute("cinemasession");
        model.addAttribute("seats",
                roomService.getSeats(
                        cinemaSession.getId(),
                        cinemaSession.getRoom().getId(),
                        seatService,
                        ticketService
                )
        );
        httpSession.setAttribute("row_index", rowIndex);
        return "cell";
    }
}
