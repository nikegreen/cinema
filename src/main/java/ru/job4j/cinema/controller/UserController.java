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

@ThreadSafe
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/formRegistration")
    public String formRegistration(Model model) {
        model.addAttribute("user", new User(0, "Гость", "password", "email", "+79999999999"));
        return "registration";
    }

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

    @GetMapping("/formLogin")
    public String formLogin(Model model,
                            HttpSession session,
                            @RequestParam(name = "fail", required = false) Boolean fail) {
        model = ModelSet.fromSession(model, session);
        model.addAttribute("fail", fail != null);
        return "login";
    }

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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/formLogin";
    }
}
