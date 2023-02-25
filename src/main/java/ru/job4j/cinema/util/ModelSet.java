package ru.job4j.cinema.util;

import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;

/**
 * Класс помошник для установки атрибута user из Http сессии
 */
public final class ModelSet {
    private  ModelSet() {
        throw new AssertionError();
    }

    /**
     * Функция для установки атрибута user тип {@link ru.job4j.cinema.model.User} из Http сессии
     * @param model тип {@link org.springframework.ui.Model}
     * @param session тип {@link javax.servlet.http.HttpSession}
     * @return тип {@link org.springframework.ui.Model}
     */
    public static Model fromSession(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername("Гость");
        }
        model.addAttribute("user", user);
        return model;
    }

}
