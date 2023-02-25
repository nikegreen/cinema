package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

/**
 * Проверка функций контроллера SessionControllerTest
 * @author nikez
 * @version $Id: $Id
 */
class UserControllerTest {
    /**
     * Проверка запроса get страница /formRegistration/
     * страница регистрации пользователя
     */
    @Test
    public void whenFormRegistration() {
        User user = new User(0, "Гость", "password", "email", "+79999999999");
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.formRegistration(model);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo("registration");
    }

    /**
     * Проверка запроса post страница /registration/
     * страница регистрации пользователя статус с успешной регистрацией
     */
    @Test
    public void whenRegistrationReturnSuccess() {
        User user = new User(0, "user1", "password", "email1", "+79999999991");
        User user1 = new User(1, "user1", "password", "email1", "+79999999991");
        UserService userService = mock(UserService.class);
        when(userService.add(user)).thenReturn(Optional.of(user1));
        UserController userController = new UserController(userService);
        String page = userController.registration(user);
        assertThat(page).isEqualTo(
                "redirect:/success?username=" + user.getUsername()
                        + "&email=" + user.getEmail()
                        + "&phone=" + user.getPhone()
        );
    }

    /**
     * Проверка запроса post страница /registration/
     * страница регистрации пользователя статус с проваленной регистрацией
     */
    @Test
    public void whenRegistrationReturnFail() {
        User user = new User(0, "user1", "password", "email1", "+79999999991");
        UserService userService = mock(UserService.class);
        when(userService.add(user)).thenReturn(Optional.empty());
        UserController userController = new UserController(userService);
        String page = userController.registration(user);
        assertThat(page).isEqualTo(
                "redirect:/fail?username=" + user.getUsername()
                        + "&email=" + user.getEmail()
                        + "&phone=" + user.getPhone()
        );
    }

    /**
     * Проверка запроса get страница /success
     * страница показывает статус успешной регистрации пользователя
     */
    @Test
    public void whenSuccess() {
        User user = new User();
        user.setUsername("Гость");
        User user1 = new User(1, "user1", "password", "email1", "+79999999991");
        User reg_user = new User(0, "user1", "", "email1", "+79999999991");
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(null);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.success(
                model, session, user1.getUsername(), user1.getEmail(), user1.getPhone()
        );
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("reg_user", reg_user);
        assertThat(page).isEqualTo("success");
    }

    /**
     * Проверка запроса get страница /fail
     * страница показывает статус проваленной регистрации пользователя
     */
    @Test
    public void whenFail() {
        User user = new User();
        user.setUsername("Гость");
        User user1 = new User(1, "user1", "password", "email1", "+79999999991");
        User reg_user = new User(0, "user1", "", "email1", "+79999999991");
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(null);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.fail(
                model, session, user1.getUsername(), user1.getEmail(), user1.getPhone()
        );
        verify(model).addAttribute("user", user);
        verify(model).addAttribute(
                "message",
                "Пользователь с такой почтой уже существует"
        );
        verify(model).addAttribute("reg_user", reg_user);
        assertThat(page).isEqualTo("fail");
    }

    /**
     * Проверка запроса get страница /formLogin
     * Страница авторизации пользователя (логин)
     * нет статуса предыдущей проваленной авторизации пользователя
     */
    @Test
    public void whenFormLoginFailFalse() {
        User user = new User();
        user.setUsername("Гость");
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(null);
        UserController userController = new UserController(userService);
        String page = userController.formLogin(model, session, null);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("fail", false);
        assertThat(page).isEqualTo("login");
    }

    /**
     * Проверка запроса get страница /formLogin?fail=true
     * Страница авторизации пользователя (логин)
     * статус предыдущей проваленной авторизации пользователя
     */
    @Test
    public void whenFormLoginFailTrue() {
        User user = new User();
        user.setUsername("Гость");
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(null);
        UserController userController = new UserController(userService);
        String page = userController.formLogin(model, session, true);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("fail", true);
        assertThat(page).isEqualTo("login");
    }

    /**
     * Проверка запроса post страница /login
     * Страница авторизации пользователя (логин)
     * данные для успешной авторизации пользователя
     * результат:
     * переход на главную страницу
     */
    @Test
    public void whenLoginFailFalse() {
        User user1 = new User(0, "", "password", "email1", "");
        User regUser = new User(1, "user1", "", "email1", "+79999999991");
        UserService userService = mock(UserService.class);
        when(userService.findUserByEmailAndPassword(
                user1.getEmail(), user1.getPassword()
        )).thenReturn(Optional.of(regUser));
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getSession()).thenReturn(session);
        UserController userController = new UserController(userService);
        String page = userController.login(user1, httpServletRequest);
        verify(httpServletRequest.getSession()).setAttribute("user", regUser);
        assertThat(page).isEqualTo("redirect:/index");
    }

    /**
     * Проверка запроса post страница /login
     * Страница авторизации пользователя (логин)
     * данные для провальной авторизации пользователя
     * результат:
     * переход на страницу авторизации с признаком ошибки
     */
    @Test
    public void whenLoginFailTrue() {
        User user1 = new User(0, "", "password", "email1", "");
        UserService userService = mock(UserService.class);
        when(userService.findUserByEmailAndPassword(
                user1.getEmail(), user1.getPassword()
        )).thenReturn(Optional.empty());
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        UserController userController = new UserController(userService);
        String page = userController.login(user1, httpServletRequest);
        assertThat(page).isEqualTo("redirect:/formLogin?fail=true");
    }

    /**
     * Проверка запроса get страница /logout
     * Страница де-авторизации пользователя (логоут)
     * результат:
     * очтстка Http сессии,
     * переход на страницу авторизации
     */

    @Test
    public void whenLogout() {
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.logout(session);
        assertThat(page).isEqualTo("redirect:/formLogin");
    }

}
