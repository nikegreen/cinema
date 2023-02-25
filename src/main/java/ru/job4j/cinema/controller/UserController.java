package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;
import ru.job4j.cinema.util.ModelSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * <p>TicketController class. Покупка билета</p>
 * @author nikez
 * @version $Id: $Id
 */
@ThreadSafe
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * <p>Окно регистрации пользователя</p>
     * @param model - объект передаёт данные в страницу
     * @return - тип {@link java.lang.String} содержит: "registration";
     */
    @GetMapping("/formRegistration")
    public String formRegistration(Model model) {
        model.addAttribute("user", new User(0, "Гость", "password", "email", "+79999999999"));
        return "registration";
    }

    /**
     * <p>Данные пользователя из окна регистрации пользователя</p>
     * @param user тип {@link ru.job4j.cinema.model.User} содержит:
     *             - имя
     *             - адрес электронной почты
     *             - номер телефона пользователя
     *             - пароль пользователя.
     * @return - тип {@link java.lang.String} содержит строку:
     * redirect:/success?username=[Username]&email=[Email]&phone=[Phone] - OK
     * redirect:/fail?username=[Username]&email=[Email]&phone=[Phone] - Ошибка регистрации.
     * Вместо строк:
     * [Username] - будет имя пользователя введенное в окне регистрации,
     * [Email] - электронная почта пользователя,
     * [Phone] - номер телефона пользователя.
     */
    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (!regUser.isPresent()) {
            return "redirect:/fail?username=" + user.getUsername()
                    + "&email=" + user.getEmail()
                    + "&phone=" + user.getPhone();
        }
        user = regUser.orElse(null);
        return "redirect:/success?username=" + user.getUsername()
                + "&email=" + user.getEmail()
                + "&phone=" + user.getPhone();
    }

    /**
     * <p>Страница об успешной регистрации</p>
     * @param model - объект передаёт данные в страницу
     * @param session - объект http сессии
     * @param username - тип {@link java.lang.String} содержит имя пользователя
     * @param email - тип {@link java.lang.String} содержит адрес электронной почты
     * @param phone - тип {@link java.lang.String} содержит номер телефона пользователя
     * @return - тип {@link java.lang.String} содержит строку: "success";
     */
    @GetMapping("/success")
    public String success(Model model, HttpSession session,
                          @RequestParam(name = "username") String username,
                          @RequestParam(name = "email") String email,
                          @RequestParam(name = "phone") String phone
    ) {
        model = ModelSet.fromSession(model, session);
        model.addAttribute("reg_user", new User(0, username, "", email, phone));
        return "success";
    }

    /**
     * <p>Страница об ошибке регистрации</p>
     * @param model - объект передаёт данные в страницу
     * @param session - объект http сессии
     * @param username - тип {@link java.lang.String} содержит имя пользователя
     * @param email - тип {@link java.lang.String} содержит адрес электронной почты
     * @param phone - тип {@link java.lang.String} содержит номер телефона пользователя
     * @return - тип {@link java.lang.String} содержит строку: "fail";
     */
    @GetMapping("/fail")
    public String fail(Model model,
                       HttpSession session,
                       @RequestParam(name = "username") String username,
                       @RequestParam(name = "email") String email,
                       @RequestParam(name = "phone") String phone
    ) {
        model = ModelSet.fromSession(model, session);
        model.addAttribute("message", "Пользователь с такой почтой уже существует");
        model.addAttribute("reg_user", new User(0, username, "", email, phone));
        return "fail";
    }

    /**
     * <p>Страница авторизации пользователя (логин)</p>
     * @param model - объект передаёт данные в страницу
     * @param session - объект http сессии
     * @param fail - статус предыдущей авторизации
     * @return - тип {@link java.lang.String} содержит строку: "login";
     */
    @GetMapping("/formLogin")
    public String formLogin(Model model,
                            HttpSession session,
                            @RequestParam(name = "fail", required = false) Boolean fail) {
        model = ModelSet.fromSession(model, session);
        model.addAttribute("fail", fail != null);
        return "login";
    }

    /**
     * <p>Данные пользователя из окна авторизации пользователя (логин)</p>
     * @param user тип {@link ru.job4j.cinema.model.User} содержит:
     *             - адрес электронной почты
     *             - пароль пользователя.
     * @param req тип {@link javax.servlet.http.HttpServletRequest}
     * @return - тип {@link java.lang.String} содержит строку:
     * "redirect:/index" - OK;
     * "redirect:/formLogin?fail=true" - ошибка;
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByEmailAndPassword(
                user.getEmail(), user.getPassword()
        );
        if (!userDb.isPresent()) {
            return "redirect:/formLogin?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    /**
     * <p>Страница убрать авторизацию пользователя (логаут)</p>
     * @param session - объект http сессии
     * @return - тип {@link java.lang.String} содержит строку: "redirect:/formLogin";
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/formLogin";
    }
}
