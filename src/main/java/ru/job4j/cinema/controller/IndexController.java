package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.util.ModelSet;

/**
 * <p>IndexController class. Spring boot index controller</p>
 * return allways text "index"
 * @author nikez
 * @version $Id: $Id
 */
@ThreadSafe
@Controller
public class IndexController {
    private final SessionService sessionService;

    public IndexController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * <p>index.</p>
     * Главная страница сайта показывет киносеансы.
     * @return a {@link String} object.
     */
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        model = ModelSet.fromSession(model, session);
        model.addAttribute("cinemasessions", sessionService.findAll());
        return "index";
    }
}
