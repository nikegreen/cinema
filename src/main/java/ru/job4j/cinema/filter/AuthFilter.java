package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;
import ru.job4j.cinema.model.User;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>AuthFilter class. Компонент spring для фильтрации файлов (права доступа)</p>
 * @author nikez
 * @version $Id: $Id
 */
@Component
public class AuthFilter implements Filter {
    /**
     * <p>Проверяет что файл картинка из каталога  /images/</p>
     * @param uri - тип {@link java.lang.String} содержит: путь и имя файла из запроса к серверу
     * @return - boolean
     *  true  - разрешить доступ, проверка на файл картинку пройдена.
     *  false - запретить доступ, проверка на файл картинку не пройдена.
     */
    private boolean isImage(String uri) {
        int res = 0;
        try {
            int index = uri.lastIndexOf("images/images");
            res = (index >= 0 &&  uri.endsWith(".jpg"))
                ? Integer.valueOf(uri.substring(index + 13, uri.length() - 4)).intValue() : 0;
        } catch (Exception e) {
            res = 0;
        }
        return  res > 0;
    }

    /**
     * <p>Метод для фильтрации запросов</p>
     * @param request - тип {@link  javax.servlet.http.HttpServletRequest} Http запрос
     * @param response - тип {@link javax.servlet.http.HttpServletResponse} Http ответ
     * @param chain - тип {@link javax.servlet.FilterChain} цепочка фильтров
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (uri.endsWith("formLogin")
                || uri.endsWith("login")
                || uri.endsWith("registration")
                || uri.endsWith("formRegistration")
                || uri.endsWith("success")
                || uri.endsWith("fail")
                || uri.endsWith("index")
                || uri.endsWith("bootstrap/css/bootstrap.min.css")
                || uri.endsWith("bootstrap/js/bootstrap.bundle.min.js")
                || uri.endsWith("login.css")
                || isImage(uri)) {
            chain.doFilter(req, res);
            return;
        }
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || "Гость".equals(user.getUsername())) {
            res.sendRedirect(req.getContextPath() + "/formLogin");
            return;
        }
        chain.doFilter(req, res);
    }
}
